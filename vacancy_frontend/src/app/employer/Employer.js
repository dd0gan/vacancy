import './Employer.css'

import React from 'react';
import { trackPromise } from 'react-promise-tracker';
import axios from 'axios';
import { withReactRouter } from "../../util/util";

import { Container } from "../components/Container";
import { Row } from "../components/Row";
import { Col } from "../components/Col";
import { Button } from "../components/Button";

import DataTable from 'react-data-table-component';

export async function loader({ params }) {
}

class Employer extends React.Component {
    constructor(props) {
        super(props);

        const columns = [
            
            {
                name: 'Favorite',
                sortable: true,
                selector: row => '♡',
                grow: 1,
            },
            {
                name: 'Employer',
                selector: row => row.username,
                sortable: false,                
                grow: 5,
                size: 1,                
                cell: (row) => (
                    <div>
                        <span>{row.username}</span>
                    </div>
                )
            },            
            {
                name: 'Email',
                sortable: true,
                selector: row => row.email,
                grow: 3,
            },
            {
                name: 'Phone',
                sortable: true,
                selector: row => row.phone,
                grow: 2,
            },
            {
                name: 'Action',
                fixed: true,
                grow: 3,
                cell: (row) => (
                    <div>
                        <Button type="button" variant="btn btn-danger"onClick={(e) => {this.handleFavoriteButton(e, row.id)}} style={{marginLeft: 5}}>
                        ♡ Make favorite</Button>
                    </div>
                )
            },
            
        ];
        
        this.state = {showPopup : false, showMessagePopup : false, messagePopupTitle : '', messagePopupBody : '',
            selectedEmployer : { id : null, type: 'Casual', status: 'OPEN'}, columns : columns, notificaions : []}
    }

    componentWillMount() {
        this.reloadEmployers();
    }

    handleFavoriteButton = (event, id) => {
        for (var i =0; i < this.state.vacancies.length; i++) {
            if (this.state.vacancies[i].id === id) {
                this.state. selectedVacancy = this.state.vacancies[i];
                break;
            }
        }
        this.setState({showPopup : true})
    }
    reloadEmployers = () => {
        trackPromise (
            axios.get('api/users').then((response) => {
                this.setState({notificaions : response.data});
            }).catch((error) => {
                let title = 'Error';
                let body = 'Exception occurred';
                this.setState({showMessagePopup : true, messagePopupTitle: title, messagePopupBody: body})
            })
        );
    }

    render() {
        return (
            <Container fluid id="Notificaion-container">
                <Row>
                    <Col>
                        <main id="Notificaion-table-wrapper">
                            <DataTable
                                title="My Employers"
                                columns={this.state.columns}
                                data={this.state.notificaions}
                                pagination
                            />
                        </main>
                    </Col>
                </Row>
            </Container>
        )
    }
}

export default withReactRouter(Employer);