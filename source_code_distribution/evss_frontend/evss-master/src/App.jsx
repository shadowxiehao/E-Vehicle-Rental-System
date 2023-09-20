import React from "react";
import { HashRouter as Router, Routes, Route, Navigate} from "react-router-dom";
import { TopMenu } from "./components/TopMenu.jsx";
import { TracePage } from "./pages/Operator/TracePage.jsx";
import { UpdatePageNew } from "./pages/Operator/UpdatePageNew.jsx";
import { LoginPage } from "./pages/LoginPage.jsx";
// import { LoginPage } from "@pages/LoginPage.jsx";
import "./App.css";
import { NotFound } from "./pages/NotFound.jsx";
class App extends React.PureComponent {
    render() {
        return (
          <Router>
            <div className="App">
                <TopMenu></TopMenu>
                <Routes>
                    <Route path="/" element={<LoginPage />}></Route>
                    <Route path="/trace" element={<TracePage />}></Route>
                    <Route path="/update" element={<UpdatePageNew />}></Route>
                    {/* <Route path="/*" element={<Navigate to="/trace"></Navigate>} /> */}
                </Routes>
            </div>
          </Router>  
        );
    }
}

export default App;