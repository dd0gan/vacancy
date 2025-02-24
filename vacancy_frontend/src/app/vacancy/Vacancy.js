import './Vacancy.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import { trackPromise } from 'react-promise-tracker';
import axios from 'axios';
import { withReactRouter } from "../../util/util";
import { isAdminUser, getCurrentUser } from "../../auth/authUtil";
import { Container, Row, Col, Button, Form, Modal, Tooltip, OverlayTrigger} from 'react-bootstrap';
import _ from "lodash";
import DataTable from 'react-data-table-component';

export async function loader({ params }) {
}

class Vacancy extends React.Component {
    constructor(props) {
        super(props);

        const columns = [
            {
                name: 'Vacancy name',
                selector: row => row.title,
                sortable: true,
                grow: 3,
            },
            {
                name: 'Skills',
                selector: row => row.skills,
                sortable: true,  
                grow: 1,              
            },
            {
                name: 'organization',
                selector: row => row.organization,
                sortable: true,    
                grow: 1,            
            },
            {
                name: 'Description',
                selector: row => row.description,
                sortable: false,
                grow: 5,
                cell: (row) => (
                    <div>
                        <span>{row.description}</span>
                    </div>
                )
            },
            {
                name: 'Hourly Rate(€) ',
                sortable: true,
                selector: row => row.hourlyRate,
            },
            {
                name: 'Working Hour(€)',
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
            },
            {
                name: 'Action',
                fixed: true,
                grow: 2,   
                cell: (row) => (
                    <div>
                        {
                            this.isApplied(row) ?
                                <OverlayTrigger overlay={<Tooltip id="tooltip-disabled">Already applied!</Tooltip>}>
                                  <span className="d-inline-block">
                                  <Button type="button" variant="success" disabled style={{ pointerEvents: 'none' }}>Apply</Button>
                                  </span>
                                </OverlayTrigger>
                                :
                                this.isClosed(row) ?
                                    <OverlayTrigger overlay={<Tooltip id="tooltip-disabled">Already closed!</Tooltip>}>
                                          <span className="d-inline-block">
                                            <Button type="button" variant="success" disabled style={{ pointerEvents: 'none' }}>Apply</Button>
                                          </span>
                                    </OverlayTrigger>
                                    :
                                    <Button type="button" 
                                    className={localStorage.getItem('roleName')=='ADMIN'?'hide':'block'}
                                    variant="success"    onClick={(e) => this.handleApplyButton(e, row.id)} >Apply</Button>

                                    
                        }
                        
                        <Button type="button" variant="primary"
                            className={localStorage.getItem('roleName')=='ADMIN'?'block':'hide'}
                             onClick={(e) => {this.handleEditButton(e, row.id)}} style={{marginLeft: 5}}>Edit</Button>                         
                        
                        <Button type="button" className={localStorage.getItem('roleName')=='ADMIN'?'block':'hide'} variant="info" onClick={(e) => {this.handleReviewButton(e, row.id)}} style={{marginLeft: 5}}>Review</Button>

                       
                    </div>
                )
            },
        ];

        this.state = {showPopup : false, showMessagePopup : false, messagePopupTitle : '', messagePopupBody : '',
            selectedVacancy : { id : null, type: 'Casual', status: 'OPEN'}, columns : columns, vacancies : []}
    }

    handlePopupClose = (event) => {
        this.setState({showPopup : false})
    }
    handleMessagePopupClose = (event) => {
        this.setState({showMessagePopup : false})
    }

    handlePopupSave = (event) => {
        event.preventDefault();
        let vacancyDto  = {
            id : this.state.selectedVacancy.id,
            hourlyRate : this.state.selectedVacancy.hourlyRate ? parseFloat(this.state.selectedVacancy.hourlyRate) : null,
            workingHour : this.state.selectedVacancy.workingHour ? parseFloat(this.state.selectedVacancy.workingHour) : null,
            type : this.state.selectedVacancy.type,
            location : this.state.selectedVacancy.location,
            status : this.state.selectedVacancy.status,
            description : this.state.selectedVacancy.description,
            title : this.state.selectedVacancy.title,
            organization : this.state.selectedVacancy.organization,
            skills : this.state.selectedVacancy.skills
        }
        trackPromise (
            axios({
                method: this.state.selectedVacancy.id ? 'put' : 'post',
                url: 'api/vacancies',
                data: vacancyDto
            }).then((response) => {
                this.setState({showPopup : false, selectedVacancy : { type: 'Casual', status: 'OPEN'}});
                this.reloadVacancies();
            }, (error) => {
                let title = 'Error';
                let body = 'Exception occurred';
                this.setState({showMessagePopup : true, messagePopupTitle: title, messagePopupBody: body})
            })
        );
    }

    handleAddButton = (event) => {
        this.setState({showPopup : true, selectedVacancy : { id : null, type: 'Casual', status: 'OPEN'}})
    }

    handleApplyButton = (event, id) => {
        trackPromise (
            axios.post('api/vacancies/apply?id=' + id, ).then((response) => {
                let title = 'Success';
                let body = response.data.message;
                this.setState({showMessagePopup : true, messagePopupTitle: title, messagePopupBody: body})
                this.reloadVacancies();
            }).catch((error) => {
                let title = 'Error';
                let body = error.response.data.message;
                this.setState({showMessagePopup : true, messagePopupTitle: title, messagePopupBody: body})
            })
        );
    }

    handleEditButton = (event, id) => {
        for (var i =0; i < this.state.vacancies.length; i++) {
            if (this.state.vacancies[i].id === id) {
                this.state.selectedVacancy = this.state.vacancies[i];
                break;
            }
        }
        this.setState({showPopup : true})
    }

    handleReviewButton = (event, id) => {
        const { navigate } = this.props;
        navigate('/dashboard/application/' + id);
    }

    handleChangePopup = (event) => {
        let id = event.target.id.replace('vacancy-form.','');
        let value = event.target.value;
        let vacancy = _.cloneDeep(this.state.selectedVacancy);
        vacancy[id] = value;
        this.setState({selectedVacancy : vacancy})
    }

    componentWillMount() {
        this.reloadVacancies();
    }

    reloadVacancies = () => {
        trackPromise (
            axios.get('api/vacancies').then((response) => {
                this.setState({vacancies : response.data});
            }).catch((error) => {
                let title = 'Error';
                let body = 'Exception occurred';
                this.setState({showMessagePopup : true, messagePopupTitle: title, messagePopupBody: body})
            })
        );
    }

    isApplied = (vacancy) => {
        for (var i =0; i < vacancy.applications.length; i++) {
            if (vacancy.applications[i].user != null
                && vacancy.applications[i].user.username === getCurrentUser().username) {
                return true;
            }
        }
        return false;
    }
 
    isClosed = (vacancy) => {
        
        return vacancy.status === 'CLOSE'
    }

    render() {
        return (
            <Container fluid id="vacancy-container">
                { isAdminUser() &&
                    <Row>
                        <Col>
                            <section className="text-left">
                                <Button type="button" className={localStorage.getItem('roleName')=='ADMIN'?'block':'hide'} variant="primary" onClick={this.handleAddButton}>Create new Vacancy</Button>
                            </section>
                        </Col>
                    </Row>
                }

                <Row>
                    <Col>
                        <main id="vacancies-table-wrapper">
                            <DataTable
                                title="Vacancies"
                                columns={this.state.columns}
                                data={this.state.vacancies}
                                pagination
                            />
                        </main>
                    </Col>
                </Row>
                <Modal show={this.state.showPopup} onHide={this.handlePopupClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>Vacancy Details</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form id="vacancy-form">
                        <Form.Group className="mb-3" controlId="vacancy-form.title">
                                <Form.Label>Title</Form.Label>
                                <Form.Control
                                    type = "text"                                   
                                    placeholder="Title"
                                    autoFocus
                                    value={this.state.selectedVacancy.title}
                                    onChange={this.handleChangePopup.bind(this)}
                                />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="vacancy-form.skills">
                                <Form.Label>Skills</Form.Label>
                                <Form.Control
                                    type = "text"
                                    placeholder="skills"
                                    value={this.state.selectedVacancy.skills}
                                    onChange={this.handleChangePopup.bind(this)}
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="vacancy-form.organization">
                                <Form.Label>Company/Organization</Form.Label>
                                <Form.Control
                                    type = "text"
                                    placeholder="organization"
                                    value={this.state.selectedVacancy.organization}
                                    onChange={this.handleChangePopup.bind(this)}
                                />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="vacancy-form.description">
                                <Form.Label>Description</Form.Label>
                                <Form.Control
                                    as = "textarea"
                                    rows = {3}
                                    placeholder="Description"
                                    autoFocus
                                    value={this.state.selectedVacancy.description}
                                    onChange={this.handleChangePopup.bind(this)}
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="vacancy-form.hourlyRate">
                                <Form.Label>Hourly rate</Form.Label>
                                <Form.Control
                                    type="number"
                                    placeholder="Hourly rate"
                                    value={this.state.selectedVacancy.hourlyRate}
                                    onChange={this.handleChangePopup.bind(this)}
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="vacancy-form.workingHour">
                                <Form.Label>Working hour</Form.Label>
                                <Form.Control
                                    type="number"
                                    placeholder="Working hour"
                                    value={this.state.selectedVacancy.workingHour}
                                    onChange={this.handleChangePopup.bind(this)}
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="vacancy-form.type">
                                <Form.Label>Type</Form.Label>
                                <Form.Control as="select"
                                              defaultValue={this.state.selectedVacancy.type}
                                              onChange={this.handleChangePopup.bind(this)}>
                                    <option value="Contract">Contract</option>
                                    <option value="Permanence">Permanence</option>
                                    <option value="Casual">Casual</option>
                                </Form.Control>
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="vacancy-form.location">
                                <Form.Label>Location</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Location"
                                    value={this.state.selectedVacancy.location}
                                    onChange={this.handleChangePopup.bind(this)}
                                />
                            </Form.Group>

                            <Form.Group className="mb-3" controlId="vacancy-form.status">
                                <Form.Label>Status</Form.Label>
                                <Form.Control as="select"
                                              defaultValue={this.state.selectedVacancy.status}
                                              onChange={this.handleChangePopup.bind(this)}>
                                    <option value="OPEN">Open</option>
                                    <option value="CLOSE">Close</option>
                                </Form.Control>
                            </Form.Group>
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button type="button" variant="secondary" onClick={this.handlePopupClose}>
                            Close
                        </Button>
                        <Button type="button" variant="primary" onClick={this.handlePopupSave}>
                            Save
                        </Button>
                    </Modal.Footer>
                </Modal>

                <Modal show={this.state.showMessagePopup} onHide={this.handleMessagePopupClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>{this.state.messagePopupTitle}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {this.state.messagePopupBody}
                    </Modal.Body>
                </Modal>
            </Container>
        )
    }
}

export default withReactRouter(Vacancy);