import React, {useEffect} from 'react';
import './App.css';
import {usePostOrganizationsFilterMutation} from "./store/types.generated";

function App() {
    const [fetchOrganizations,refetchOrganizations] = usePostOrganizationsFilterMutation()

    async function fetchOrganizationsWithLog() {
        await fetchOrganizations({
            organizationFilters: {
                pagination: {
                    page: 0,
                    size: 10,
                },
            }
        }).unwrap()
            .then(console.log)
            .catch(console.error)
    }

    useEffect(() => {
        const intervalId = setInterval(fetchOrganizationsWithLog, 1000)
        return () => clearInterval(intervalId)
    }, [refetchOrganizations])

    return (
        <div className="App">
            <header className="App-header">
                <p>
                    Edit <code>src/App.tsx</code> and save to reload.
                </p>
                <a
                    className="App-link"
                    href="https://reactjs.org"
                    target="_blank"
                    rel="noopener noreferrer"
                >
                    Learn React
                </a>
            </header>
        </div>
    );
}

export default App;
