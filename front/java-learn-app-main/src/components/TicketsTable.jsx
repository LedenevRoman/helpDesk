import React from "react";
import PropTypes from "prop-types";
import {
  ButtonGroup,
  Button,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TablePagination,
  TableRow,
  TextField, Typography, TableSortLabel,
} from "@material-ui/core";
import {Link, Redirect} from "react-router-dom";
import { withRouter } from "react-router";
import { TICKETS_TABLE_COLUMNS } from "../constants/tablesColumns";
import dateFormat from "dateformat";

class TicketsTable extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      orderBy: "default",
      order: "asc"
    }
  }

  handleSortRequest = async (columnId) => {
    const isAscId = this.state.orderBy === columnId && this.state.order === "asc";
    await this.setState({
      order: isAscId ? 'desc' : 'asc',
      orderBy: columnId
    });
    this.props.pageSortCallback(this.state.order, this.state.orderBy);
  }

  handleChangePage = async (event, newPage) => {
    await this.props.changePageCallback(newPage);
  };

  handleChangeRowsPerPage = async (event) => {
    await this.props.changeSizeCallback(+event.target.value, 0);
  };

  handleAction = (id, action) => {
    const token = localStorage.getItem("authorization");
    const url = new URL('http://localhost:8080/ticket/action');
    url.searchParams.append('ticketId', id);
    url.searchParams.append('actionName', action);

    fetch(url.toString(), {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        'Authorization': 'Bearer ' + token
      },
    }).then(() => {
      this.props.updatePageCallback();
    });
  }

  render() {
    const { searchCallback, tickets } = this.props;
    const { url } = this.props.match;
    const {
      handleChangePage,
      handleChangeRowsPerPage,
      handleAction,
    } = this;

    return (
      <Paper>
        <TableContainer>
          <TextField
            onChange={searchCallback}
            id="filled-full-width"
            label="Search"
            style={{ margin: 5, width: "500px" }}
            placeholder="Search for ticket"
            margin="normal"
            InputLabelProps={{
              shrink: true,
            }}
          />
          <Table>
            <TableHead>
              <TableRow>
                {TICKETS_TABLE_COLUMNS.map((column) => (
                  <TableCell align={column.align} key={column.id}>
                    {column.id !== "actions" && (
                        <TableSortLabel
                            direction={this.state.orderBy === column.id ? this.state.order : 'asc'}
                            onClick={async () => {await this.handleSortRequest(column.id)}}>
                          <b>{column.label}</b>
                        </TableSortLabel>
                    )}
                    {column.id === "actions" && (
                          <b>{column.label}</b>
                    )}
                  </TableCell>
                ))}
              </TableRow>
            </TableHead>
            <TableBody>
              {tickets.map((row, index) => {
                  return (
                    <TableRow hover role="checkbox" key={index}>
                      {TICKETS_TABLE_COLUMNS.map((column) => {
                        const value = row[column.id];
                        if (column.id === "name") {
                          return (
                            <TableCell key={column.id}>
                              <Link to={`${url}/${row.id}`}>{value}</Link>
                            </TableCell>
                          );
                        }
                        if (column.id === "desiredResolutionDate") {
                          return <TableCell align="left" key={column.id}>
                                <Typography>
                                  {dateFormat(Date.parse(row.desiredResolutionDate), "dd/mm/yyyy")}
                                </Typography>
                          </TableCell>
                        }
                        if (column.id === "actions") {
                          return <TableCell align="center" key={column.id}>
                            {row.feedbackDto.id !== null && (<Button
                                        component={Link}
                                        to={`/feedback/${row.id}`}
                                        color="primary"
                                        variant="contained"
                                    >
                                      VIEW FEEDBACK
                                    </Button>
                            )}
                          {Object.keys(row.actions).map((el) => {
                            if (row.actions[el] === "Leave feedback" && row.feedbackDto.id === null) {
                              return (
                                  <Button
                                      component={Link}
                                      to={`/feedback/${row.id}`}
                                      color="primary"
                                      variant="contained"
                                  >
                                    {row.actions[el]}
                                  </Button>
                              )
                            } else {
                              return (
                                  <Button
                                      onClick={() => handleAction(row.id, row.actions[el])}
                                      variant="contained"
                                      color="primary"
                                  >
                                    {row.actions[el]}
                                  </Button>
                              );
                            }
                          })}
                        </TableCell>
                        } else {
                          return <TableCell key={column.id}>{value}</TableCell>;
                        }
                      })}
                    </TableRow>
                  );
                })}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
          rowsPerPageOptions={[5, 10, 25, 100]}
          component="div"
          count={this.props.count}
          rowsPerPage={this.props.size}
          page={this.props.page}

          onChangePage={handleChangePage}
          onChangeRowsPerPage={handleChangeRowsPerPage}
        />
      </Paper>
    );
  }
}

TicketsTable.propTypes = {
  searchCallback: PropTypes.func,
  updatePageCallback: PropTypes.func,
  tickets: PropTypes.array,
};

const TicketsTableWithRouter = withRouter(TicketsTable);
export default TicketsTableWithRouter;
