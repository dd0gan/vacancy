import './Settings.css'

import React from 'react';
import { trackPromise } from 'react-promise-tracker';
import axios from 'axios';
import { withReactRouter } from "../../util/util";
import { Container, Row, Col, Button, Form, Modal, Tooltip, OverlayTrigger} from 'react-bootstrap';

export async function loader({ params }) {
}

class Settings extends React.Component {


    initialState = { currentPassword:'',newPassword:'',confirmPassword:'' }

    state = this.initialState


    constructor(props) {
        super(props);
        this.state = {  currentPassword : '', newPassword :'',confirmPassword:'', showPopup : false}
        
    }
    componentWillMount() {
        this.reloadSettings();
    }
    handleReset = (event) => {        
        this.setState(() => this.initialState);             
    }
    handleSaveButton = (event) => {
        alert('hi');
        let title = 'Saved';
        let body = 'Setting saved successfully...!' ;
        this.setState({showPopup : true, popupTitle: title, popupBody: body})
    }
    reloadSettings = () => {
        trackPromise (
            axios.get('api/users').then((response) => {
                this.setState({notificaions : response.data});
            }).catch((error) => {
                let title = 'Error';
                let body = 'Exception occurred';
                alert(body);
                this.setState({showMessagePopup : true, messagePopupTitle: title, messagePopupBody: body})
            })
        );
    }
    handlePopupClose = (event) => {
        this.setState({showPopup : false})
    }
    render() {
        return (
            <Container fluid id="Settings-container">
                <Row>
                    <Col>
                        <main id="Settings-table-wrapper">
                        <h4>My Settings</h4>

                        
                        <label>Current password:</label>
                        <input type='password' class='form-control' placeholder="Current password"  value={this.state.currentPassword}/>


                        <label>New password:</label>
                        <input type='password' class='form-control' placeholder="New password" value={this.state.newPassword}/>


                        <label>Confirm password:</label>
                        <input type='password' class='form-control' placeholder="Confirm password" value={this.state.confirmPassword}/>

                        <button type="submit" className="btn btn-primary" onClick={ this.handleSaveButton}>
                            Submit
                        </button>
                            &nbsp;
                        <button type="reset" className="btn btn-primary" onClick={ this.handleReset}>
                        Reset
                        </button>
                        </main>
                    </Col>
                </Row>

                <Modal show={this.state.showPopup} onHide={this.handlePopupClose}>
                <Modal.Header closeButton>
                    <Modal.Title>{this.state.popupTitle}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {this.state.popupBody}
                </Modal.Body>
                </Modal>
            </Container>
        );
        
    }
}

export default withReactRouter(Settings);