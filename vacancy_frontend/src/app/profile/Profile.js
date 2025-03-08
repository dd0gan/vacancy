import './Profile.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import { trackPromise } from 'react-promise-tracker';
import axios from 'axios';
import { withReactRouter } from "../../util/util";

import {Alert, Container, Form, Row, Col, Button, Modal} from "react-bootstrap";
import { AuthContext } from '../../auth/AuthContext';

export async function loader({ params }) {
}

class Profile extends React.Component {
    static contextType = AuthContext

    constructor(props) {
        super(props);
        this.state = {selectedFile : null, selectedFileLabel : 'Curriculum Vitae' ,showMessagePopup : false, cvUniqueId : '', cvFilename : ''}
    }

    componentWillMount() {
        this.state.username = this.context
        this.getUserDetail();
    }

    getUserDetail = () => {
        trackPromise (
            axios({
                method: 'get',
                url : 'api/users/me',
            }).then((response) => {
                this.setState({
                    cvUniqueId : response.data.cvUniqueId, 
                    cvFilename : response.data.cvFilename,
                    email : response.data.email, 
                    phone : response.data.phone})
            }, (error) => {
                let title = 'Error';
                let body = 'Exception occurred';
                this.setState({showMessagePopup : true, messagePopupTitle: title, messagePopupBody: body})
            })
        );
    }

    handleMessagePopupClose = (event) => {
        this.setState({showMessagePopup : false})
    }

    handleChangeFileUpload = (event) => {
        this.state.selectedFile = event.target.files[0];
        this.setState({selectedFileLabel : event.target.files[0].name})
    }

    handleFileSave = (event) => {
        event.preventDefault();
        const formData = new FormData();
        formData.append("file", this.state.selectedFile);

        trackPromise (
            axios({
                method: 'post',
                url : 'api/users/cv/upload',
                data : formData,
                headers: { "Content-Type": "multipart/form-data" }
            }).then((response) => {
                let title = 'Success';
                let body = 'Upload successful';
                this.setState({showMessagePopup : true, messagePopupTitle: title, messagePopupBody: body})
                this.getUserDetail();
            }, (error) => {
                let title = 'Error';
                let body = 'Exception occurred';
                this.setState({showMessagePopup : true, messagePopupTitle: title, messagePopupBody: body})
            })
        );
    }

    getCvDownloadUrl = () => {
        const token = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).token : null;
        if (token) {
            return process.env.REACT_APP_API_URL + '/api/users/cv/download?authToken=' + token +'&fileId=' + this.state.cvUniqueId
        }
    }

    render() {
        return (

            <Container fluid>
                <div>
                <Row>
                    <Col>
                    <h4>Profile</h4>                      
                    <hr></hr>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <text>Username  </text>
                        <input type='text' class='form-control' placeholder="Email address" disabled value={this.state.username}/>
                        
                        <br></br>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <text> Your CV (resume) : </text>
                    </Col>
                </Row>
                <Alert key="cv-current" variant="primary">
                    {
                        this.state.cvUniqueId ?
                        <a href={this.getCvDownloadUrl()}>{ this.state.cvFilename }</a>
                            :
                            <div>Please upload CV</div>
                    }
                </Alert>

                <Row>
                    <Col>
                        <Form id="profile-form">
                            <Form.Group className="sm-3" controlId="profile-form.cv">
                                <Form.Label custom>Upload</Form.Label>
                                <Form.File
                                    type="file"
                                    label={this.state.selectedFileLabel}
                                    id = "cv"
                                    onChange={this.handleChangeFileUpload.bind(this)}
                                    custom
                                />
                            </Form.Group>

                            <Button type="button" variant="primary" onClick={this.handleFileSave}>
                            Upload
                            </Button>
                        </Form>
                    </Col>
                    <Col></Col>
                </Row>
                <hr></hr>
                
                <Row>
                    <Col>                        
                        <text>Full name:  </text>
                        <input type='text' class='form-control' placeholder="Full name" value={this.state.name}/>

                    </Col>
                </Row>

                <Row>
                    <Col>                        
                        <text>Email  address </text>                        
                        <input type='text' class='form-control' placeholder="Email address" value={this.state.email}/>
                    </Col>
                </Row>

                <Row>
                    <Col>                        
                        <text>Phone number </text>
                        <input type='number' class='form-control' placeholder="Phone number" value={this.state.phone}/>

                    </Col>
                </Row>

              
                <Button type="button" variant="primary" onClick={this.handleFileSave}>
                            Update profile
                    </Button>
                <Modal show={this.state.showMessagePopup} onHide={this.handleMessagePopupClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>{this.state.messagePopupTitle}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {this.state.messagePopupBody}
                    </Modal.Body>
                </Modal>
                </div>
            </Container>
        )
    }
}

export default withReactRouter(Profile);