import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App, {
    loader as appLoader,
} from './app/App';

import Auth , {
    loader as authLoader,
} from './auth/Auth';

import Vacancy , {
    loader as vacancyLoader,
} from './app/vacancy/Vacancy';

import Settings , {
    loader as settingsLoader,
} from './app/settings/Settings';

import Employer , {
    loader as employerLoader,
} from './app/employer/Employer';


import Notification , {
    loader as notificationLoader,
} from './app/notification/Notification';

import Profile , {
    loader as profileLoader,
} from './app/profile/Profile';

import Application , {
    loader as applicationLoader,
} from './app/application/Application';

import {
    Navigate,
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";

import { usePromiseTracker } from "react-promise-tracker";
import * as Loader from "react-loader-spinner";
import { ProtectedRoute } from './auth/authUtil';

import './config/axiosConfig';

const LoadingIndicator = props => {
    const { promiseInProgress } = usePromiseTracker();

    return (
        promiseInProgress &&
        <Loader promiseTracker={usePromiseTracker} />
    );
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <div>
        <BrowserRouter>
            <Routes>
                <Route path="/dashboard" element={<ProtectedRoute><App /></ProtectedRoute>} loader={appLoader}>
                    <Route path="/dashboard/vacancy" element={<Vacancy/>} loader={vacancyLoader}/>
                    <Route path="/dashboard/settings" element={<Settings/>} loader={settingsLoader}/>
                    <Route path="/dashboard/employer" element={<Employer/>} loader={employerLoader}/>
                    <Route path="/dashboard/notification" element={<Notification/>} loader={notificationLoader}/>
                    <Route path="/dashboard/profile" element={<Profile/>} loader={profileLoader}/>
                    <Route path="/dashboard/application/:id" element={<Application/>} loader={applicationLoader}/>
                </Route>

                <Route path="/auth" element={<Auth />} loader={authLoader}>
                </Route>
                <Route path="*" element={<Navigate to="/dashboard" replace/>} />
               
            </Routes>
        </BrowserRouter>  
        <LoadingIndicator/>
    </div>
);
