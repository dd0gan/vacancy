import './App.css';
import React from 'react';
import {withReactRouter} from "../util/util";
import { Navbar, Nav, Link, NavDropdown } from 'react-bootstrap'
import { isAdminUser,getCurrentUser } from "../auth/authUtil";
import {
  Outlet
} from "react-router-dom";
import { AuthContext } from "../auth/AuthContext";

export async function loader({ params }) {
}

class App extends React.Component {
  constructor(props) {
    super(props);
  }

  componentWillMount() {
    this.setState({user : JSON.parse(localStorage.getItem ('user'))});
  }

  handleProfileClick = (event) => {
    event.preventDefault();
    const { navigate } = this.props;
    navigate('/dashboard/profile');
  }

  handleLogout = (event) => {
    event.preventDefault();
    localStorage.clear();
    const { navigate } = this.props;
    navigate('/auth');
  }

  render() {
    return (
        <main className="App">
          
           <div class='userType'>
              <p>
                <img src='../../images/user.png' alt='' height="25px" width="25px"></img>
                <span>
                  {localStorage.getItem('roleName')=='ADMIN'?'Employer user account' : 'Candidate user account'}
              </span>
              </p>
            </div>
          

          <AuthContext.Provider value={ this.state.user.username }>
            <Navbar bg="light" expand="lg">
              
              <NavDropdown title={ "Hi, " + this.state.user.username } id="basic-nav-dropdown">
                <NavDropdown.Item href="" onClick={this.handleProfileClick}>Profile</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="#" onClick={this.handleLogout}>Logout</NavDropdown.Item>
              </NavDropdown>
              
              <Navbar.Toggle aria-controls="basic-navbar-nav" />
              <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="mr-auto">
                <Nav.Link href="/dashboard/">Dashboard</Nav.Link>
                  <Nav.Link href="/dashboard/vacancy">Vacancy</Nav.Link>
                  <Nav.Link href="/dashboard/notification">Notification</Nav.Link>
                  <Nav.Link href="/dashboard/employer"  className={localStorage.getItem('roleName')=='ADMIN'?'hide':'block'}>Employer</Nav.Link>
                  <Nav.Link href="/dashboard/settings">Settings</Nav.Link>
                </Nav>
                
              </Navbar.Collapse>
         
             
            </Navbar>
            
            <Outlet/>
          </AuthContext.Provider>
                  <div id='banner' class="dashboard"> 
                    <img src='../../images/bg.jpg' alt='' className={localStorage.getItem('roleName')=='ADMIN'?'hide':'block'}></img>
                    <img src='../../images/bg2.png' alt='' className={localStorage.getItem('roleName')=='ADMIN'?'block':'hide'}></img>
                  </div>
                     
        </main>
    );
  }
}

export default withReactRouter(App);
