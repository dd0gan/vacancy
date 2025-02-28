import './Auth.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import { trackPromise } from 'react-promise-tracker';
import axios from 'axios';
import { Modal } from 'react-bootstrap';
import { withReactRouter } from "../util/util";

export async function loader({ params }) {
}

class Auth extends React.Component {

    initialState = { username: '', password: '' , roleName: 'USER' }

    state = this.initialState


    constructor(props) {
        super(props);
        this.state = { view : 'signin', loading : false, username : '', password :'',roleName:'USER', showPopup : false}

        this.handleChangeUsername = this.handleChangeUsername.bind(this);
        this.handleChangePassword = this.handleChangePassword.bind(this);
        this.handleRole = this.handleRole.bind(this);
    }

    handleChangeView = (e) => {
        e.preventDefault();
        if (this.state.view === 'signup') {
            this.setState({view :'signin'});
        } else {
            this.setState({view :'signup'});
        }
    }

    isSignIn = () => {
        return this.state.view === 'signin';
    }

    handleSignIn = (event) => {
        event.preventDefault();
        let message = this.validateUsernamePassword();

        if (message.length > 0) {
            this.setState({showPopup : true, popupTitle: 'Validation Error', popupBody: message})
        } else {
            trackPromise (
                axios.post('/api/auth/signIn' , {
                    'username' : this.state.username,
                    'password' : this.state.password,
                    'roleName' : this.state.roleName
                }).then((response) => {
                    let data = response.data;
                    
                    localStorage.setItem('user', JSON.stringify(data))
                    localStorage.setItem('roleName', this.state.roleName)
                    const { navigate } = this.props;
                    navigate('/dashboard');
                }).catch((error) => {
                    let title = 'Error';
                    let body = 'Username or password is incorrect';
                    this.setState({showPopup : true, popupTitle: title, popupBody: body})
                })
            );
        }
    }
    handleReset = (event) => {        
        this.setState(() => this.initialState);             
    }
    handleSignUp = (event) => {
        event.preventDefault();

        let message = this.validateUsernamePassword();
        //alert(this.state.roleName);
        //return;
        if (message.length > 0) {
            this.setState({showPopup : true, popupTitle: 'Validation Error', popupBody: message})
        } else {
            trackPromise (
                axios.post('/api/auth/signUp' , {
                    'username' : this.state.username,
                    'password' : this.state.password,
                    'roleName' : this.state.roleName
                }).then((response) => {
                    let title = 'Success';
                    let body = 'User is created';
                    this.setState({showPopup : true, popupTitle: title, popupBody: body})
                    this.setState({view :'signin'});
                }).catch((error) => {
                    
                    let title = 'Error';
                    let body = 'Username or password is invalid' + error.message;
                    this.setState({showPopup : true, popupTitle: title, popupBody: body})
                })
            );
        }
    }

    handleChangeUsername = (event) => {
        this.setState({username : event.target.value})
    }

    handleChangePassword = (event) => {
        this.setState({password : event.target.value})
    }
    handleRole = (event) => {
        this.setState({roleName : event.target.value})
    }
    handlePopupClose = (event) => {
        this.setState({showPopup : false})
    }

    validateUsernamePassword = (event) => {
        let validationMessage = "";
        if (this.state.username.length < 2) {
            validationMessage += "user name should have at least 2 characters <br/>" 
        }
        if (this.state.password.length < 2) {
            validationMessage += "password should have at least 2 characters<br/>" 
        }
        return validationMessage;
    }

    render() {
        return (
            <main className="Auth-form-container">
                <form className="Auth-form">
                    <div className="Auth-form-content">
                        <h3 className="Auth-form-title"> {this.isSignIn() ? 'Sign In' : 'Sign Up'}</h3>
                        <section className="form-group mt-3">
                            <label>Username</label>
                            <input
                                type="text"
                                className="form-control mt-1"
                                placeholder="Enter username"
                                value={this.state.username}
                                onChange={ this.handleChangeUsername.bind(this) }
                            />
                        </section>
                        <section className="form-group mt-3">
                            <label>Password</label>
                            <input
                                type="password"
                                className="form-control mt-1"
                                placeholder="Enter password"
                                value={this.state.password}
                                onChange={ this.handleChangePassword.bind(this)}
                            />
                        </section>

                        <section className="form-group mt-3">
                            <label>
                                I am 
                                </label>                            
                            <select name="role_name" placeholder="select me" className="form-control mt-1"                             
                            onChange={ this.handleRole.bind(this)}> 
                                <option value="USER">Job seeker</option>
                                <option value="ADMIN">Employer </option>                                                                
                            </select>
                        </section>

                        <section className="d-grid gap-2 mt-3">
                            <button type="submit" className="btn btn-primary" onClick={this.isSignIn() ? this.handleSignIn : this.handleSignUp}>
                            Submit
                            </button>
                            &nbsp;
                            <button type="reset" className="btn btn-primary" onClick={ this.handleReset} >
                            Reset
                            </button>
                        </section>
                        <p className="sign-up text-right mt-2">
                            {this.isSignIn() ? 'Sign up?' : 'Sign in?'}  <a href="#" onClick={this.handleChangeView}>{this.isSignIn() ? 'Register here' : 'Login here'}</a>
                        </p>
                    </div>
                </form>

                <Modal show={this.state.showPopup} onHide={this.handlePopupClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>{this.state.popupTitle}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {this.state.popupBody}
                    </Modal.Body>
                </Modal>
            </main>



        );
    }

}

export default withReactRouter(Auth);
