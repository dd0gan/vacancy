import './Notification.css'
import React from 'react';
import { trackPromise } from 'react-promise-tracker';
import axios from 'axios';
import { withReactRouter } from "../../util/util";
import DataTable from 'react-data-table-component';

export async function loader({ params }) {
}

class Notification extends React.Component {
    constructor(props) {
        super(props);

        const columns = [
            
            
            {
                name: 'Notification',
                selector: row => row.description,
                sortable: false,                
                grow: 10,
                size: 1,                
                cell: (row) => (
                    <div>
                        <span>{row.description}</span>
                    </div>
                )
            },            
            {
                name: 'Date',
                sortable: true,
                selector: row => row.createdOn,
                grow: 1,
            },
            
        ];
        
        this.state = {showPopup : false, showMessagePopup : false, messagePopupTitle : '', messagePopupBody : '',
            selectedNotification : { id : null, type: 'Casual', status: 'OPEN'}, columns : columns, notificaions : []}
    }

    componentWillMount() {
        this.reloadNotifications();
    }

    reloadNotifications = () => {
        trackPromise (
            axios.get('api/notificaion').then((response) => {
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
            <div fluid id="Notificaion-container">
                <tr>
                    <td>
                        <main id="Notificaion-table-wrapper">
                            <DataTable
                                title="My Notifications"
                                columns={this.state.columns}
                                data={this.state.notificaions}
                                pagination
                            />
                        </main>
                    </td>
                </tr>
            </div>
        )
    }
}

export default withReactRouter(Notification);