import React from "react";
import {
    Button,
    InputLabel,
    FormControl,
    MenuItem,
    Select,
    TextField,
    Typography,
} from "@material-ui/core";
import {Link, Redirect, withRouter} from "react-router-dom";
import RequestConstructor from "../services/RequestConstructor";
import {RATE_OPTIONS} from "../constants/inputsValues";

class FeedbackPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            id: null,
            name: "",
            ownerName: "",
            authorizedUserRole: "",
            feedbackText: "",
            feedbackRate: "",
            feedbackId: null,
            redirect: false
        };

    }

    async componentDidMount() {
        // set request for getting ticket in draft state
        const token = localStorage.getItem("authorization");
        if (token !== null) {
            const ticketUrl = new URL('http://localhost:8080/ticket/');
            const ticketFromUrl = this.props.location.pathname.split("/");
            const ticketId = ticketFromUrl[ticketFromUrl.length - 1];
            let ticketData = await RequestConstructor.createAuthorizedRequest(ticketUrl.toString() + ticketId, 'GET', token);

            if (ticketData) {
                this.setState({
                    id: ticketData.id,
                    name: ticketData.name,
                    ownerName: ticketData.ownerName,
                    authorizedUserRole: ticketData.authorizedUserRole,
                    feedbackText: ticketData.feedbackDto.text,
                    feedbackRate: ticketData.feedbackDto.rateName,
                    feedbackId: ticketData.feedbackDto.id
                })
            }
        }
    }

    handleRateChange = (event) => {
        this.setState({
            feedbackRate: event.target.value,
        });
    };

    handleFeedbackTextChange = (event) => {
        this.setState({
            feedbackText: event.target.value,
        });
    };

    handleSubmitFeedback = async () => {
        if (!this.state.feedbackRate) {
            await this.setState({
                feedbackRate: "VERY_GOOD"
            })
        }
        const token = localStorage.getItem("authorization");
        const url = new URL('http://localhost:8080/feedback/new');
        url.searchParams.append("ticketId", this.state.id)
        const feedback = {
            text: this.state.feedbackText,
            rateName: this.state.feedbackRate
        }
        await fetch(url.toString(), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify(feedback)
        });

    this.setState({
                      redirect: true
                  });
    }

    render() {
        const {
            id,
            name,
            ownerName,
            authorizedUserRole,
            feedbackText,
            feedbackRate,
            feedbackId,
            redirect
        } = this.state;

        if (!localStorage.getItem("authorization")) {
            return <Redirect to="/"/>
        }

        if (redirect) {
            return <Redirect to="/main-page"/>
        }

        return (
            <div className="ticket-creation-form-container">
                <header className="ticket-creation-form-container__navigation-container">
                    <Button component={Link} to={`/ticket-info/${id}`} variant="contained">
                        Ticket Info
                    </Button>
                    <Button component={Link} to="/main-page" variant="contained">
                        Main Page
                    </Button>
                </header>
                <div className="ticket-creation-form-container__title">
                    <Typography variant="h4">{`Ticket(${id}) - ${name}`}</Typography>
                </div>
                <div className="ticket-creation-form-container__title">
                    {(authorizedUserRole !== "ENGINEER" && !feedbackId) && (
                        <Typography display="block" variant="h6">
                            Dear {ownerName}, please, rate your satisfaction with the solution:
                        </Typography>
                    )}
                    {feedbackId && (
                        <Typography display="block" variant="h6">
                            Your feedback is:
                        </Typography>
                    )}
                </div>
                <br/>
                <div  className="inputs-section">
                    {(authorizedUserRole !== "ENGINEER" && !feedbackId) && (<FormControl variant="outlined" required>
                        <InputLabel align="center" shrink htmlFor="category-label">
                            Rate
                        </InputLabel>
                        <Select
                            align="center"
                            value={feedbackRate || "VERY_GOOD"}
                            label="Rate"
                            onChange={this.handleRateChange}
                            inputProps={{
                                name: "rate",
                                id: "rate-label",
                            }}
                        >
                            {RATE_OPTIONS.map((item, index) => {
                                return (
                                    <MenuItem value={item.value} key={index}>
                                        {item.label}
                                    </MenuItem>
                                );
                            })}
                        </Select>
                    </FormControl>
                    )}
                    {feedbackId && (
                        <Typography display="block" variant="h5">
                            {feedbackRate}
                        </Typography>
                    )}
                </div>
                <div className="inputs-section">
                    {(authorizedUserRole !== "ENGINEER" && !feedbackId) && (<FormControl>
                        <TextField
                            label="There is some feedback about an Admin work with a ticket."
                            multiline
                            rows={4}
                            variant="outlined"
                            value={feedbackText}
                            className="creation-text-field creation-text-field_width680"
                            onChange={this.handleFeedbackTextChange}
                        />
                    </FormControl>
                    )}
                    {feedbackId && (
                        <Typography display="block" variant="h5">
                            {feedbackText}
                        </Typography>
                    )}
                </div>
                <div>
                    {(authorizedUserRole !== "ENGINEER" && !feedbackId) && (
                        <section className="submit-button-section">
                        <Button
                            variant="contained"
                            onClick={this.handleSubmitFeedback}
                            color="primary"
                        >
                            Submit
                        </Button>
                    </section>
                    )}
                </div>
            </div>
        )
    }
}

const FeedbackPageWithRouter = withRouter(FeedbackPage);
export default FeedbackPageWithRouter;