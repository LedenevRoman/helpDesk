import React from "react";
import PropTypes from "prop-types";
import CommentsTable from "./CommentsTable";
import HistoryTable from "./HistoryTable";
import TabPanel from "./TabPanel";
import TicketCreationPageWithRouter from "./TicketCreationPage";
import {Link, Redirect, Route, Switch} from "react-router-dom";
import { withRouter } from "react-router";
import { ALL_TICKETS } from "../constants/mockTickets";
import { COMMENTS } from "../constants/mockComments";
import { HISTORY } from "../constants/mockHistory";
import dateFormat from "dateformat";

import {
  Button,
  ButtonGroup,
  Paper,
  Tab,
  Tabs,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableRow,
  Typography,
  TextField,
} from "@material-ui/core";
import RequestConstructor from "../services/RequestConstructor";
import {CATEGORIES_OPTIONS} from "../constants/inputsValues";

function a11yProps(index) {
  return {
    id: `full-width-tab-${index}`,
    "aria-controls": `full-width-tabpanel-${index}`,
  };
}

class TicketInfo extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      commentValue: "",
      tabValue: 0,
      ticketComments: [],
      ticketHistory: [],
      currentUser: {
        name: "Dave Brubeck",
        id: 4242,
      },
      ticketData: {
        id: 0,
        name: "",
        createdOn: "",
        categoryId: 0,
        status: "",
        urgencyName: "",
        desiredResolutionDate:  dateFormat(Date.parse(""), "dd/mm/yyyy"),
        ownerName: "",
        approverName: "",
        assigneeName: "",
        attachments: [],
        description: "",
        actions: [],
        authorizedUserRole: "",
        feedback: "",
        authorizedUserName: ""
      },
    };
  }

  async componentDidMount() {
    // get required ticket by id
    const token = localStorage.getItem("authorization");
    if (token !== null) {
      await this.preloadPage();
    }
  }

  preloadPage = async () => {
      const { ticketId } = this.props.match.params;
      const token = localStorage.getItem("authorization");
      const ticketUrl = new URL('http://localhost:8080/ticket/' + ticketId);
      const ticket = await RequestConstructor.createAuthorizedRequest(ticketUrl.toString(), 'GET', token);
      this.setState({
          ticketData: {
              id: ticket.id,
              createdOn: dateFormat(Date.parse(ticket.createdOn), "dd/mm/yyyy"),
              desiredResolutionDate: dateFormat(Date.parse(ticket.desiredResolutionDate), "dd/mm/yyyy"),
              name: ticket.name,
              status: ticket.status,
              urgencyName: ticket.urgencyName,
              actions: ticket.actions,
              categoryId: ticket.categoryId - 1,
              ownerName: ticket.ownerName,
              assigneeName: ticket.assigneeName,
              approverName: ticket.approverName,
              attachments: ticket.attachmentsDto,
              description: ticket.description,
              authorizedUserRole: ticket.authorizedUserRole,
              feedback: ticket.feedbackDto,
              authorizedUserName: ticket.authorizedUserName

          },
      });

      const historyUrl = new URL('http://localhost:8080/history/' + ticketId);
      const history = await RequestConstructor.createAuthorizedRequest(historyUrl.toString(), 'GET', token);
      this.setState({
          ticketHistory: history
      });

      const commentUrl = new URL('http://localhost:8080/comment/all/' + ticketId);
      const comment = await RequestConstructor.createAuthorizedRequest(commentUrl.toString(), 'GET', token);
      this.setState({
          ticketComments: comment
      });
  }

  handleTabChange = (event, value) => {
    this.setState({
      tabValue: value,
    });
  };

  handleEnterComment = (event) => {
    this.setState({
      commentValue: event.target.value,
    });
  };

  addComment = async () => {
    // put request for comment creation here
    const { ticketId } = this.props.match.params;
    const commentText = {"text": this.state.commentValue};
    const token = localStorage.getItem("authorization");
    const commentUrl = new URL('http://localhost:8080/comment/' + ticketId);
    const response = await RequestConstructor.createAuthorizedRequest(commentUrl, 'POST', token, commentText);

    const newComment = {
      date: response.date,
      userEmail: response.userEmail,
      text: response.text,
    };

    this.setState({
      ticketComments: [...this.state.ticketComments, newComment],
      commentValue: "",
    });
  };

  handleAction = async (id, action) => {
    const token = localStorage.getItem("authorization");
    const url= new URL('http://localhost:8080/ticket/action');
    url.searchParams.append('ticketId', id);
    url.searchParams.append('actionName', action);
    fetch(url.toString(), {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        'Authorization': 'Bearer ' + token
      },
    }).then(() => {
      this.preloadPage();
    });
    window.location.reload();
  }

  handleDownloadAttachment = async (attachmentId, attachmentName) => {
    const fileDownload = require("js-file-download");
    fetch('http://localhost:8080/attachment/' + attachmentId, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        'Authorization': 'Bearer ' + localStorage.getItem("authorization")
      },
    }).then(function (response) {
      return response.blob();
    }).then(function (blob) {
      fileDownload(blob, attachmentName);
    })
  }

  handleSubmitTicket = () => {
    // set ticket status to 'submitted'
    console.log("SUBMIT ticket");
  };

  handleEditTicket = () => {
    console.log("EDIT ticket");
  };

  handleCancelTicket = () => {
    // set ticket status to 'canceled' status
    console.log("CANCEL ticket");
  };

  render() {
    const {
      approverName,
      id,
      name,
      createdOn,
      categoryId,
      status,
      urgencyName,
      desiredResolutionDate,
      ownerName,
      assigneeName,
      attachments,
      description,
      actions,
      feedback,
      authorizedUserName
    } = this.state.ticketData;

    const { commentValue, tabValue, ticketComments, ticketHistory } =
      this.state;

    const { url } = this.props.match;

    const { handleAction, handleEditTicket } = this;

    if (!localStorage.getItem("authorization")) {
      return <Redirect to="/"/>
    }

    return (
      <Switch>
        <Route exact path={url}>
          <div className="ticket-data-container">
            <div className={"ticket-data-container__back-button back-button"}>
              <Button component={Link} to="/main-page" variant="contained">
                Ticket list
              </Button>
            </div>
            <div className="ticket-data-container__title">
              <Typography variant="h4">{`Ticket(${id}) - ${name}`}</Typography>
            </div>
            <div className="ticket-data-container__info">
              <TableContainer className="ticket-table" component={Paper}>
                <Table>
                  <TableBody>
                    <TableRow>
                      <TableCell>
                        <Typography align="left" variant="subtitle1">
                          Created on:
                        </Typography>
                      </TableCell>
                      <TableCell>
                        <Typography align="left" variant="subtitle1">
                          {createdOn}
                        </Typography>
                      </TableCell>
                    </TableRow>
                    <TableRow>
                      <TableCell>
                        <Typography align="left" variant="subtitle1">
                          Category:
                        </Typography>
                      </TableCell>
                      <TableCell>
                        <Typography align="left" variant="subtitle1"
                          value={categoryId}>
                            {CATEGORIES_OPTIONS[categoryId].label}
                        </Typography>
                      </TableCell>
                    </TableRow>
                    <TableRow>
                      <TableCell>
                        <Typography align="left" variant="subtitle1">
                          Status:
                        </Typography>
                      </TableCell>
                      <TableCell>
                        <Typography align="left" variant="subtitle1">
                          {status}
                        </Typography>
                      </TableCell>
                    </TableRow>
                    <TableRow>
                      <TableCell>
                        <Typography align="left" variant="subtitle1">
                          Urgency:
                        </Typography>
                      </TableCell>
                      <TableCell>
                        <Typography align="left" variant="subtitle1">
                          {urgencyName}
                        </Typography>
                      </TableCell>
                    </TableRow>
                    <TableRow>
                      <TableCell>
                        <Typography align="left" variant="subtitle1">
                          Desired Resolution Date:
                        </Typography>
                      </TableCell>
                      <TableCell>
                        <Typography align="left" variant="subtitle1">
                          {desiredResolutionDate}
                        </Typography>
                      </TableCell>
                    </TableRow>
                    <TableRow>
                      <TableCell>
                        <Typography align="left" variant="subtitle1">
                          Owner:
                        </Typography>
                      </TableCell>
                      <TableCell>
                        <Typography align="left" variant="subtitle1">
                          {ownerName}
                        </Typography>
                      </TableCell>
                    </TableRow>
                    <TableRow>
                      <TableCell>
                        <Typography align="left" variant="subtitle1">
                          Approver:
                        </Typography>
                      </TableCell>
                      <TableCell>
                        <Typography align="left" variant="subtitle1">
                          {approverName || "Not assigned"}
                        </Typography>
                      </TableCell>
                    </TableRow>
                    <TableRow>
                      <TableCell>
                        <Typography align="left" variant="subtitle1">
                          Assignee:
                        </Typography>
                      </TableCell>
                      <TableCell>
                        <Typography align="left" variant="subtitle1">
                          {assigneeName || "Not assigned"}
                        </Typography>
                      </TableCell>
                    </TableRow>
                    <TableRow>
                      <TableCell>
                        <Typography align="left" variant="subtitle1">
                          Attachments:
                        </Typography>
                      </TableCell>
                      <TableCell>
                          {attachments.length !== 0 && (
                              Object.keys(attachments).map((el) => {
                                return (
                                    <Button
                                        onClick={() => this.handleDownloadAttachment(attachments[el].id, attachments[el].name)}
                                        variant="contained"
                                        color="primary"
                                    >
                                      {attachments[el].name}
                                    </Button>
                                )
                              })
                          )
                            }
                        {attachments.length === 0 && (
                            <Typography align="left" variant="subtitle1">
                              {"No attached files"}
                            </Typography>
                        )
                        }
                      </TableCell>
                    </TableRow>
                    <TableRow>
                      <TableCell>
                        <Typography align="left" variant="subtitle1">
                          Description:
                        </Typography>
                      </TableCell>
                      <TableCell>
                        <Typography align="left" variant="subtitle1">
                          {description || "Not assigned"}
                        </Typography>
                      </TableCell>
                    </TableRow>
                  </TableBody>
                </Table>
              </TableContainer>
            </div>

              <div className="ticket-data-container__button-section">
                <ButtonGroup variant="contained" color="primary">
                  {Object.keys(actions).map((el) => {
                    if (actions[el] === "Leave feedback" && feedback.id === null) {
                      return (
                          <Button
                              component={Link}
                              to={`/feedback/${id}`}
                              color="primary"
                              variant="contained"
                          >
                            {actions[el]}
                          </Button>
                      )
                    } else {
                      return (
                          <Button
                              onClick={() => handleAction(id, actions[el])}
                              variant="contained"
                              color="primary"
                          >
                            {actions[el]}
                          </Button>
                      );
                    }
                  })}
                  {(status === "Draft" && authorizedUserName === ownerName) && (
                  <Button
                    component={Link}
                    to={`/create-ticket/${id}`}
                    onClick={handleEditTicket}
                  >
                    Edit
                  </Button>
                  )}
                  {(status === "Declined" && authorizedUserName === ownerName) && (
                      <Button
                          component={Link}
                          to={`/create-ticket/${id}`}
                          onClick={handleEditTicket}
                      >
                        Edit
                      </Button>
                  )}
                  {feedback.id !== null && (<Button
                          component={Link}
                          to={`/feedback/${id}`}
                          color="primary"
                          variant="contained"
                      >
                        VIEW FEEDBACK
                      </Button>
                  )}
                </ButtonGroup>
              </div>

            <div className="ticket-data-container__comments-section comments-section">
              <div className="">
                <Tabs
                  variant="fullWidth"
                  onChange={this.handleTabChange}
                  value={tabValue}
                  indicatorColor="primary"
                  textColor="primary"
                >
                  <Tab label="History" {...a11yProps(0)} />
                  <Tab label="Comments" {...a11yProps(1)} />
                </Tabs>
                <TabPanel value={tabValue} index={0}>
                  <HistoryTable history={ticketHistory} />
                </TabPanel>
                <TabPanel value={tabValue} index={1}>
                  <CommentsTable comments={ticketComments} />
                </TabPanel>
              </div>
            </div>
            {tabValue && (
              <div className="ticket-data-container__enter-comment-section enter-comment-section">
                <TextField
                  label="Enter a comment"
                  multiline
                  rows={4}
                  value={commentValue}
                  variant="filled"
                  className="comment-text-field"
                  onChange={this.handleEnterComment}
                />
                <div className="enter-comment-section__add-comment-button">
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={this.addComment}
                  >
                    Add Comment
                  </Button>
                </div>
              </div>
            )}
          </div>
        </Route>
        <Route path="/create-ticket/:ticketId">
          <TicketCreationPageWithRouter />
        </Route>
      </Switch>
    );
  }
}

TicketInfo.propTypes = {
  match: PropTypes.object,
};

const TicketInfoWithRouter = withRouter(TicketInfo);
export default TicketInfoWithRouter;
