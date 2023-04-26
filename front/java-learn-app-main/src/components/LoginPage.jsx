import React from "react";
import { Button, TextField, Typography } from "@material-ui/core";
import RequestConstructor from "../services/RequestConstructor";

class LoginPage extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      email: "",
      password: "",
      error: ""
    };
  }

  handleNameChange = (event) => {
    this.setState({ email: event.target.value });
  };

  handlePasswordChange = (event) => {
    this.setState({ password: event.target.value });
  };

  handleClickAuth = async () => {
    // put authorization logic here

    const response = await RequestConstructor.createAuthenticationRequest('http://localhost:8080/auth', this.state)
        .catch(response => {
          console.log(response.message);
        });

    const result = await response.json();

    if (response.ok) {
      localStorage.setItem("authorization", result.token);
      this.props.authCallback(true);
    } else {
      if (this.state.error !== result.message) {
        await this.setState({
          error: result.message
        })
      }
    }
  }
  
  render() {
    return (
      <div className="container">
        <div className="container__title-wrapper">
          <Typography component="h2" variant="h3">
            Login to the Help Desk
          </Typography>
        </div>
        <div className="container__from-wrapper">
          <form>
            <div className="container__inputs-wrapper">
              <div className="form__input-wrapper">
                <TextField
                  onChange={this.handleNameChange}
                  label="User name"
                  variant="outlined"
                  placeholder="User name"
                />
              </div>
              <div className="form__input-wrapper">
                <TextField
                  onChange={this.handlePasswordChange}
                  label="Password"
                  variant="outlined"
                  type="password"
                  placeholder="Password"
                />
              </div>
            </div>
          </form>
        </div>
        <div className="container__button-wrapper">
          <Button
            size="large"
            variant="contained"
            color="primary"
            onClick={this.handleClickAuth}
          >
            Enter
          </Button>
        </div>
        <br/>
        <div>
          {this.state.error !== "" && (
                <Typography component="h2" variant="h6" color="secondary">
                  {this.state.error}
                </Typography>
          )}
        </div>
      </div>
    );
  }
}

export default LoginPage;
