import React from 'react';
import { Provider } from 'react-redux';
import { ToastContainer } from 'react-toastify';
import { store } from './store/store';
import OrganizationsPage from './components/OrganizationsPage';
import 'react-toastify/dist/ReactToastify.css';
import './App.css';

function App() {
  return (
    <Provider store={store}>
      <div className="App">
        <header className="app-header">
          <p>SOA Lab2</p>
        </header>
        
        <main className="app-main">
          <OrganizationsPage />
        </main>
        
        <ToastContainer
          position="top-right"
          autoClose={5000}
          hideProgressBar={false}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
        />
      </div>
    </Provider>
  );
}

export default App;