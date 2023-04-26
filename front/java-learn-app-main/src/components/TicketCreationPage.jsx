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
import { ALL_TICKETS } from "../constants/mockTickets";
import { CATEGORIES_OPTIONS, URGENCY_OPTIONS  } from "../constants/inputsValues";
import RequestConstructor from "../services/RequestConstructor";
import dateFormat from "dateformat";

class TicketCreationPage extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      id: null,
      categoryId: 4,
      name: "",
      description: "",
      urgencyName: "Critical",
      desiredResolutionDate: "",
      attachments: [],
      attachmentsPersist: [],
      commentText: "",
      redirect: false,
      error: ""
    };
    console.log(JSON.stringify(this.state.attachmentsPersist));
  }

  async componentDidMount() {
    // set request for getting ticket in draft state
    const token = localStorage.getItem("authorization");
    if (token !== null) {
      const ticketUrl = new URL('http://localhost:8080/ticket/');
      const ticketFromUrl = this.props.location.pathname.split("/");
      const ticketId = ticketFromUrl[ticketFromUrl.length - 1];
      let ticketData = null;
      if (ticketId !== "create-ticket") {
        ticketData = await RequestConstructor.createAuthorizedRequest(ticketUrl.toString() + ticketId, 'GET', token);
      }

      if (ticketData) {
        this.setState({
          id: ticketData.id,
          name: ticketData.name,
          desiredResolutionDate: ticketData.desiredResolutionDate,
          description: ticketData.description,
          urgencyName: ticketData.urgencyName,
          categoryId: ticketData.categoryId,
          attachments: ticketData.attachmentsDto
        });
      }
    }
  }

  preloadPage = async () => {
    const token = localStorage.getItem("authorization");
    const ticketUrl = new URL('http://localhost:8080/ticket/');
    const ticketFromUrl = this.props.location.pathname.split("/");
    const ticketId = ticketFromUrl[ticketFromUrl.length - 1];
    let ticketData = await RequestConstructor.createAuthorizedRequest(ticketUrl.toString() + ticketId, 'GET', token);

    if (ticketData) {
      this.setState({
        attachments: ticketData.attachmentsDto
      });
    }
  }

  handleCategoryChange = (event) => {
    this.setState({
      categoryId: event.target.value,
    });
  };

  handleNameChange = (event) => {
    this.setState({
      name: event.target.value,
    });
  };

  handleDescriptionChange = (event) => {
    this.setState({
      description: event.target.value,
    });
  };

  handleUrgencyChange = (event) => {
    this.setState({
      urgencyName: event.target.value,
    });
  };

  handleResolutionDate = (event) => {
    this.setState({
      desiredResolutionDate: event.target.value,
    });
  };

  handleAttachmentPersistChange = (event) => {
    let array = this.state.attachmentsPersist;
    for (let i = 0; i < event.target.files.length; i++) {
      array.push(event.target.files[i])
    }
    this.setState({
      attachmentsPersist: array
    });
  };

  handleDeleteAttachment = async (attachmentId) => {
    const token = localStorage.getItem("authorization");
    const ticketFromUrl = this.props.location.pathname.split("/");
    const ticketId = ticketFromUrl[ticketFromUrl.length - 1];
    const url = new URL('http://localhost:8080/attachment/remove');
    url.searchParams.append("ticketId", ticketId);
    url.searchParams.append("attachmentId", attachmentId);
    fetch(url.toString(), {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        'Authorization': 'Bearer ' + token
      },
    }).then(async () => {
      await this.preloadPage();
    });
  }

  handleDeleteAttachmentPersist = async (attachment) => {
    let filteringArray = this.state.attachmentsPersist.filter(item => item !== attachment);
    await this.setState({
      attachmentsPersist: filteringArray
    });
  }

  handleCommentChange = (event) => {
    this.setState({
      commentText: event.target.value,
    });
  };

  handleSaveDraft = async () => {
    const token = localStorage.getItem("authorization");
    if (this.state.id === null) {
      const url = new URL('http://localhost:8080/ticket/draft');
      await this.requestForSave(url, token);
    } else {
      const url = new URL('http://localhost:8080/ticket/edit-draft');
      await this.requestForEdit(url, token);
    }
  };

  handleSubmitTicket = async () => {
    const token = localStorage.getItem("authorization");
    if (this.state.id === null) {
      const url = new URL('http://localhost:8080/ticket/new');
      await this.requestForSave(url, token);
    } else {
      const url = new URL('http://localhost:8080/ticket/edit-new');
      await this.requestForEdit(url, token);
    }
  };

  async requestForSave(url, token) {
    const response = await fetch(url.toString(), {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        'Authorization': 'Bearer ' + token
      },
      body: JSON.stringify(this.state)
    });
    let result = await response.json();

    if (response.ok) {
      if (this.state.attachmentsPersist.length > 0) {
        const array = this.state.attachmentsPersist;
        let formData = new FormData();
        for (let i = 0; i < array.length; i++) {
          formData.append('file', array[i]);
        }

        const urlAttachment = new URL('http://localhost:8080/attachment/add/');

        const responseAttach = await fetch(urlAttachment.toString() + result, {
          method: 'POST',
          headers: {
            'Authorization': 'Bearer ' + token,
          },
          body: formData
        }).catch(async () => {
          await this.setState({
            redirect: false
          })
        });
        if (responseAttach.ok) {
          this.setState({
            id: result,
            redirect: true
          });
        }
        const resultAttach = await responseAttach.json();

        if (!responseAttach.ok) {
          const url = new URL('http://localhost:8080/ticket/');
          await fetch(url.toString() + result, {
            method: 'DELETE',
            headers: {
              'Authorization': 'Bearer ' + token,
            },
          });
          await this.setState({
            id: null,
            redirect: false
          })
          if (this.state.error !== resultAttach.message) {
            await this.setState({
              error: resultAttach.message,
            });
          }
        }
      } else {
        this.setState({
          id: result,
          redirect: true
        });
      }
    } else {
      if (this.state.error !== result.message) {
        await this.setState({
          error: result.message
        })
      }
    }
  }

  async requestForEdit(url, token) {
    url.searchParams.append("ticketId", this.state.id);
    const response = await fetch(url.toString(), {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        'Authorization': 'Bearer ' + token
      },
      body: JSON.stringify(this.state)
    });
    let result = await response.json();

    if (response.ok) {
      if (this.state.attachmentsPersist.length > 0) {
        const array = this.state.attachmentsPersist;
        let formData = new FormData();
        for (let i = 0; i < array.length; i++) {
          formData.append('file', array[i]);
        }

        const urlAttachment = new URL('http://localhost:8080/attachment/add/');

        const responseAttach = await fetch(urlAttachment.toString() + this.state.id, {
          method: 'POST',
          headers: {
            'Authorization': 'Bearer ' + token,
          },
          body: formData
        }).catch(async () => {
          await this.setState({
            redirect: false
          })
        });
        if (responseAttach.ok) {
          this.setState({
            redirect: true
          });
        }
        const resultAttach = await responseAttach.json();

        if (!responseAttach.ok) {
          await this.setState({
            redirect: false
          });
          if (this.state.error !== resultAttach.message) {
            await this.setState({
              error: resultAttach.message
            });
          }
        }
      } else {
        this.setState({
          redirect: true
        });
      }
    } else {
      if (this.state.error !== result.message) {
        await this.setState({
          error: result.message
        });
      }
    }
  }

  render() {
    const {
      id,
      name,
      categoryId,
      redirect,
      description,
      attachments,
      attachmentsPersist,
      desiredResolutionDate,
      commentText,
      urgencyName,
    } = this.state;

    if (!localStorage.getItem("authorization")) {
      return <Redirect to="/"/>
    }

    if (redirect) {
      return <Redirect to={`/ticket-info/${id}`}/>
    }

    return (
      <div className="ticket-creation-form-container">
        <header className="ticket-creation-form-container__navigation-container">
          <Button component={Link} to="/main-page" variant="contained">
            Ticket List
          </Button>
        </header>
        <div className="ticket-creation-form-container__title">
          <Typography display="block" variant="h3">
            Create new ticket
          </Typography>
        </div>
        <div className="ticket-creation-form-container__form">
          <div className="inputs-section">
            <div className="ticket-creation-form-container__inputs-section inputs-section__ticket-creation-input ticket-creation-input ticket-creation-input_width200">
              <FormControl>
                <TextField
                  required
                  label="Name"
                  variant="outlined"
                  onChange={this.handleNameChange}
                  id="name-label"
                  value={name}
                />
              </FormControl>
            </div>
            <div className="inputs-section__ticket-creation-input ticket-creation-input ticket-creation-input_width200">
              <FormControl variant="outlined" required>
                <InputLabel shrink htmlFor="category-label">
                  Category
                </InputLabel>
                <Select
                  value={categoryId}
                  label="Category"
                  onChange={this.handleCategoryChange}
                  inputProps={{
                    name: "category",
                    id: "category-label",
                  }}
                >
                  {CATEGORIES_OPTIONS.map((item, index) => {
                    return (
                      <MenuItem value={item.value} key={index}>
                        {item.label}
                      </MenuItem>
                    );
                  })}
                </Select>
              </FormControl>
            </div>
            <div className="inputs-section__ticket-creation-input ticket-creation-input">
              <FormControl variant="outlined" required>
                <InputLabel shrink htmlFor="urgency-label">
                  Urgency
                </InputLabel>
                <Select
                  value={urgencyName}
                  label="Urgency"
                  onChange={this.handleUrgencyChange}
                  className={"ticket-creation-input_width200"}
                  inputProps={{
                    name: "urgency",
                    id: "urgency-label",
                  }}
                >
                  {URGENCY_OPTIONS.map((item, index) => {
                    return (
                      <MenuItem value={item.value} key={index}>
                        {item.label}
                      </MenuItem>
                    );
                  })}
                </Select>
              </FormControl>
            </div>
          </div>
          <div className="inputs-section-attachment">
            <div className="inputs-section__ticket-creation-input ticket-creation-input ticket-creation-input_width200">
              <FormControl>
                <InputLabel shrink htmlFor="urgency-label">
                  Desired resolution date
                </InputLabel>
                <TextField
                  onChange={this.handleResolutionDate}
                  label="Desired resolution date"
                  type="date"
                  id="resolution-date"
                  value={desiredResolutionDate}
                  InputLabelProps={{
                    shrink: true,
                  }}
                />
              </FormControl>
            </div>
            <div className="ticket-creation-input">
              <FormControl>
                <Typography variant="caption">Add attachment</Typography>
                <TextField
                    onChange={this.handleAttachmentPersistChange}
                    type="file"
                    multiple
                    variant="outlined"
                />
              </FormControl>
            </div>
          </div>
          <br/>
          <div align="center">

            {Object.keys(attachmentsPersist).map((el) => {
              return (
                  <Button
                      onClick={() => this.handleDeleteAttachmentPersist(attachmentsPersist[el])}
                      variant="contained"
                      color="primary"
                  >
                    {attachmentsPersist[el].name}
                  </Button>
              )
            })
            }
          </div>
        <br/>
          <div align="center">
          {Object.keys(attachments).map((el) => {
                return (
                    <Button
                        onClick={() => this.handleDeleteAttachment(attachments[el].id)}
                        variant="contained"
                        color="primary"
                    >
                      {attachments[el].name}
                    </Button>
                )
              })
          }
          </div>
          <div className="inputs-section">
            <FormControl>
              <TextField
                label="Description"
                multiline
                rows={4}
                variant="outlined"
                value={description}
                className="creation-text-field creation-text-field_width680"
                onChange={this.handleDescriptionChange}
              />
            </FormControl>
          </div>
          <div className="inputs-section">
            <FormControl>
              <TextField
                label="Comment"
                multiline
                rows={4}
                variant="outlined"
                value={commentText}
                className="creation-text-field creation-text-field_width680"
                onChange={this.handleCommentChange}
              />
            </FormControl>
          </div>
          <section className="submit-button-section">
            <Button variant="contained" onClick={this.handleSaveDraft}>
              Save as Draft
            </Button>
            <Button
              variant="contained"
              onClick={this.handleSubmitTicket}
              color="primary"
            >
              Submit
            </Button>
          </section>
        </div>
        <br/>
        <div>
          {this.state.error !== "" && (
              <Typography align="center" component="h2" variant="h6" color="secondary">
                {this.state.error}
              </Typography>
          )}
        </div>
      </div>
    );
  }
}

const TicketCreationPageWithRouter = withRouter(TicketCreationPage);
export default TicketCreationPageWithRouter;
