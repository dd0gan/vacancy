import './Application.css';
import React from 'react';
import { trackPromise } from 'react-promise-tracker';
import axios from 'axios';
import { Container } from "../components/Container";
import { Row } from "../components/Row";
import { Col } from "../components/Col";
import { Button } from "../components/Button";
import DataTable from "react-data-table-component";
import { withReactRouter } from "../../util/util";
import { isAdminUser } from "../../auth/authUtil";

export async function loader({ params }) {
}

class Application extends React.Component {

    constructor(props) {
        super(props);

        const columns = [
            {
                name: 'ID',
                selector: row => row.id,
                sortable: true,
            },
            {
                name: 'Description',
                selector: row => row.description,
                sortable: false,
                cell: (row) => (
                    <div>
                        <span>{row.description}</span>
                    </div>
                )
            },
            {
                name: 'Hourly Rate',
                sortable: true,
                selector: row => row.hourlyRate,
            },
            {
                name: 'Working Hour',
                sortable: true,
                selector: row => row.workingHour,
            },
            {
                name: 'Type',
                sortable: true,
                selector: row => row.type,
            },
            {
                name: 'Location',
                sortable: true,
                selector: row => row.location,
            },
            {
                name: 'Status',
                sortable: true,
                selector: row => row.status,
            }
        ];

        const applicationColumns = [
            {
                name: 'ID',
                selector: row => row.id,
                sortable: true,
            },
            {
                name: 'User',
                sortable: true,
                selector: row => row.user['username'],
            },
            {
                name: 'Status',
                sortable: true,
                selector: row => row.status,
            },

            {
                name: 'CV',
                fixed: true,
                cell: (row) => (
                    <div>
                        {
                            row.user['cvUniqueId'] ?
                                <a href={ this.getCvDownloadUrl(row.user['cvUniqueId'])}>{ row.user['cvFilename']}</a>
                                :
                                <div>No CV</div>
                        }
                    </div>

                )
            },


            {
                name: 'Action',
                fixed: true,
                cell: (row) => (
                    <div>
                        {
                            isAdminUser() &&
                            <Button type="button" variant="success" onClick={(e) => {this.handleAcceptButton(e, row.id)}}>Accept</Button>
                        }
                        {
                            isAdminUser() &&
                            <Button type="button" variant="danger" onClick={(e) => {this.handleRejectButton(e, row.id)}} style={{marginLeft: 5}}>Reject</Button>
                        }
                    </div>
                )
            },
        ];
        this.state = {id : props.match.params.id, columns : columns, applicationColumns: applicationColumns};

    }

    componentWillMount() {
        this.getSelectedVacancies();
    }

    getSelectedVacancies() {
        trackPromise (
            axios.get('api/vacancies/' + this.state.id, ).then((response) => {
                this.setState({selectedVacancy : response.data, vacancies : [response.data], applications : response.data.applications})
            }).catch((error) => {
                let title = 'Error';
                let body = error.response.data.message;
                this.setState({showMessagePopup : true, messagePopupTitle: title, messagePopupBody: body})
            })
        );
    }

    handleMessagePopupClose = (event) => {
        this.setState({showMessagePopup : false})
    }

    handleAcceptButton = (event, id) => {
        this.completeAction(id, 'ACCEPTED');
    }

    handleRejectButton = (event, id) => {
        this.completeAction(id, 'REJECTED');
    }

    completeAction(applicationId, acceptReject) {
        trackPromise (
            axios.post('api/vacancies/complete?id=' + this.state.id + '&applicationId=' + applicationId + '&acceptReject=' + acceptReject, ).then((response) => {
                this.getSelectedVacancies();
                let title = 'Success';
                let body = response.data.message;

                alert(title + '\n' + body);
            }).catch((error) => {
                let title = 'Error';
                let body = error.response.data.message;
                alert(title + '\n' + body);
            })
        );
    }notification

    getCvDownloadUrl = (cvUniqueId) => {
        const token = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).token : null;
        if (token) {
            return process.env.REACT_APP_API_URL + '/api/users/cv/download?authToken=' + token +'&fileId=' + cvUniqueId
        }
    }

    render() {
        return (

            <Container fluid>
                <div variant='info'>
                    <Row>
                        <Col>
                            <section id="application-review-table-wrapper">
                                <DataTable
                                    title="Vacancy Review"
                                    columns={ this.state.columns }
                                    data={ this.state.vacancies }
                                />
                            </section>
                        </Col>
                    </Row>
                </div>
                <hr/>
                <Row>
                    <Col>
                        <section id="application-list-table-wrapper">
                            <DataTable
                                title="Applications"
                                columns={ this.state.applicationColumns }
                                data={ this.state.applications }
                            />
                        </section>
                    </Col>
                </Row>
            </Container>
        )
    }
}

export default withReactRouter(Application);