import React from "react";
import TabPanel from "./TabPanel";
import TicketsTable from "./TicketsTable";
import { AppBar, Button, Tab, Tabs } from "@material-ui/core";
import {Link, Switch, Route, Redirect} from "react-router-dom";
import { withRouter } from "react-router";
import TicketInfoWithRouter from "./TicketInfo";
import RequestConstructor from "../services/RequestConstructor";
function a11yProps(index) {
  return {
    id: `full-width-tab-${index}`,
    "aria-controls": `full-width-tabpanel-${index}`,
  };
}
class MainPage extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      prop: 42,
      tabValue: 0,
      myTickets: [],
      allTickets: [],
      filterMyTickets: "",
      filterAllTickets: "",
      pageNumber: 0,
      pageSize: 5,
      countMyTickets: 0,
      countAllTickets: 0,
      order: 'asc',
      orderBy: 'default',
      authorizedUserRole: "",
    };
  }

  async componentDidMount() {
    const token = localStorage.getItem("authorization");
    if (token !== null) {
      const role = await RequestConstructor.createAuthorizedRequest('http://localhost:8080/auth', 'GET', token);
      await this.setState({
        authorizedUserRole: role
      });
      await this.preloadPage();
    }
  }

  handleChangePage = async (pageNumber) => {
    await this.setState({
      pageNumber: pageNumber,
    })
    await this.preloadPage();
  }

  handleChangeSize = async (pageSize, pageNumber) => {
    await this.setState({
      pageSize: pageSize,
      pageNumber: pageNumber,
    })
    await this.preloadPage();
  }

  handleChangeSort = async (order, orderBy) => {
    await this.setState({
      order: order,
      orderBy: orderBy
    })
    await this.preloadPage();
  }

  preloadPage = async () => {
    const token = localStorage.getItem("authorization");
    if (token !== null) {
      if (this.state.tabValue === 0) {
        const urlMyTickets = new URL('http://localhost:8080/ticket/my');
        urlMyTickets.searchParams.append('pageNumber', this.state.pageNumber);
        urlMyTickets.searchParams.append('pageSize', this.state.pageSize);
        urlMyTickets.searchParams.append('orderBy', this.state.orderBy);
        urlMyTickets.searchParams.append('order', this.state.order);
        urlMyTickets.searchParams.append('filter', this.state.filterMyTickets);
        await RequestConstructor.createAuthorizedRequest(urlMyTickets.toString(), 'GET', token).then(response => {
          this.setState({
            myTickets: response.content,
            countMyTickets: response.totalElements,
          });
        });
      }

      if (this.state.tabValue === 1) {
        const allTicketsUrl = new URL('http://localhost:8080/ticket/all');
        allTicketsUrl.searchParams.append('pageNumber', this.state.pageNumber);
        allTicketsUrl.searchParams.append('pageSize', this.state.pageSize);
        allTicketsUrl.searchParams.append('orderBy', this.state.orderBy);
        allTicketsUrl.searchParams.append('order', this.state.order);
        allTicketsUrl.searchParams.append('filter', this.state.filterAllTickets);
        await RequestConstructor.createAuthorizedRequest(allTicketsUrl.toString(), 'GET', token).then(response => {
          this.setState({
            allTickets: response.content,
            countAllTickets: response.totalElements,
          });
        });
      }
    }
  }

  handleLogout = () => {
    localStorage.removeItem("authorization");
    this.props.authCallback(false);
  };

  handleTabChange = async (event, value) => {
    await this.setState({
      tabValue: value,
      filterMyTickets: "",
      filterAllTickets: "",
      pageNumber: 0,
      pageSize: 5,
    });
    await this.preloadPage();
  };

  handleSearchTicket = async (event) => {
    const { tabValue } = this.state;

    if (tabValue === 0) {
      await this.setState({
        filterMyTickets: event.target.value
      });
    }
    if (tabValue === 1) {
      await this.setState({
        filterAllTickets: event.target.value
      });
    }
    await this.preloadPage();
  };

  render() {
    const { allTickets, myTickets, tabValue } = this.state;
    const { path } = this.props.match;
    const { handleSearchTicket } = this;

    if (!localStorage.getItem("authorization")) {
      return <Redirect to="/"/>
    }

    return (
      <>
        <Switch>
          <Route exact path={path}>
            <div className="buttons-container">
              {this.state.authorizedUserRole !== "ENGINEER" && (
                  <Button
                component={Link}
                to="/create-ticket"
                variant="contained"
                color="primary"
              >
                Create Ticket
              </Button>
              )}
              <Button
                component={Link}
                to="/"
                onClick={this.handleLogout}
                variant="contained"
                color="secondary"
              >
                Logout
              </Button>
            </div>
            <div className="table-container">
              <AppBar position="static">
                <Tabs
                  variant="fullWidth"
                  onChange={this.handleTabChange}
                  value={tabValue}
                >
                  <Tab label="My tickets" {...a11yProps(0)} />
                  <Tab label="All tickets" {...a11yProps(1)} />
                </Tabs>
                <TabPanel value={tabValue} index={0}>
                  <TicketsTable
                    searchCallback={handleSearchTicket}
                    updatePageCallback={this.preloadPage}
                    changePageCallback={this.handleChangePage}
                    changeSizeCallback={this.handleChangeSize}
                    count={this.state.countMyTickets}
                    size={this.state.pageSize}
                    page={this.state.pageNumber}
                    pageSortCallback={this.handleChangeSort}
                    tickets={myTickets}
                  />
                </TabPanel>
                <TabPanel value={tabValue} index={1}>
                  <TicketsTable
                    searchCallback={handleSearchTicket}
                    updatePageCallback={this.preloadPage}
                    changePageCallback={this.handleChangePage}
                    changeSizeCallback={this.handleChangeSize}
                    count={this.state.countAllTickets}
                    size={this.state.pageSize}
                    page={this.state.pageNumber}
                    pageSortCallback={this.handleChangeSort}
                    tickets={allTickets}
                  />
                </TabPanel>
              </AppBar>
            </div>
          </Route>
          <Route path={`${path}/:ticketId`}>
            <TicketInfoWithRouter />
          </Route>
        </Switch>
      </>
    );
  }
}

const MainPageWithRouter = withRouter(MainPage);
export default MainPageWithRouter;
