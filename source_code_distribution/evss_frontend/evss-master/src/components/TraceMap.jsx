// import React, { useEffect, useState } from "react";
// import { Map, Marker, GoogleApiWrapper, InfoWindow } from "google-maps-react";

// import "../styles/tracemap.css"

// const customizeMap = {
//     width: '100%',
//     height: '100%'
// };

// const insureMarks = [
//     { latitude: 55.8733057, longitude: -4.0884047 },
//     { latitude: 55.9734057, longitude: -4.4844047 },
//     { latitude: 55.6933057, longitude: -4.2824047 },
//     { latitude: 55.7733057, longitude: -4.2984047 },
//     { latitude: 55.803057, longitude: -4.3884047 },
//     { latitude: 55.8233057, longitude: -4.1884047 },
// ]

// export const TraceMap = (props) => {
//     const [showingInfoWindow, setShowingInfoWindow] = useState(false);
//     const [activeMarker, setActiveMarker] = useState({});
//     const [selectedPlace, setSelectedPlace] = useState({});
//     const [marks, setMarks] = useState(insureMarks);

//     useEffect( () => {
//         setMarks(props.locations);
//     }, []);   //if locations data change, just re-render maps

//     const onMarkerClick = (props, marker, e) => {
//         setActiveMarker(marker);
//         setSelectedPlace(props);
//         setShowingInfoWindow(true);
//     }

//     const drawMarker = () => {
//         // console.log("some data");
//         return marks.map((store, i) => {
//             return <Marker 
//                 key={i} 
//                 id={i} 
//                 position={{
//                     lat: store.latitude,
//                     lng: store.longitude,  
//                 }}
//                 onClick={onMarkerClick}
//                 name="i"
//             />
//         })
//     }

//     return (
//         <div className="map-container">
//             <Map
//                 style={customizeMap}
//                 google={props.google}
//                 zoom={20}
//                 centerAroundCurrentLocation={true}
//             >

//                 { drawMarker() }
//                 <InfoWindow
//                     marker={activeMarker}
//                     visible={showingInfoWindow}
//                 >
//                     <div>
//                         <h1>{selectedPlace.name}</h1>
//                     </div>
//                 </InfoWindow>
//             </Map>
//         </div>
//     );
// }

// export default GoogleApiWrapper({
//     apiKey: "AIzaSyDeypXv1R31N6-IxBnr4xbuo2UtUELwnUo",
//     v: "3.30"
// })(TraceMap);


import React, { Component } from "react";
import { Map, InfoWindow, Marker, GoogleApiWrapper } from "google-maps-react";

const insureMarks = [
    { latitude: 55.8733057, longitude: -4.0884047 },
    { latitude: 55.9734057, longitude: -4.4844047 },
    { latitude: 55.6933057, longitude: -4.2824047 },
    { latitude: 55.7733057, longitude: -4.2984047 },
    { latitude: 55.803057, longitude: -4.3884047 },
    { latitude: 55.8233057, longitude: -4.1884047 },
]

const insureMes = [
    { id: 5, battery: 56, sn: "AABBFF45353" },
    { id: 6, battery: 9, sn: "AABBFF42423" },
    { id: 7, battery: 77, sn: "AABBDD55353" },
    { id: 8, battery: 5, sn: "AABBCC75453" },
    { id: 9, battery: 100, sn: "AAEEFF46353" },
    { id: 10, battery: 4, sn: "AASSFF88553" },
]

export class TraceMap extends Component {
    constructor(props) {
        super(props);
        this.onMarkerClick = this.onMarkerClick.bind(this);
        this.state = {
            showingInfoWindow: false,
            activeMarker: {},
            selectedPlace: {},
            marks: insureMarks,
            messages: insureMes,
            markList: [],
        };
    }

    componentDidMount() {
        this.setState({
            marks: this.props.locations,
            messages: this.props.messages,
        });
    }


    onMarkerClick(props, marker, e) {
        this.setState({
            selectedPlace: props,
            activeMarker: marker,
            showingInfoWindow: true
        });
    }

    // drawMarker = () => {
    //     // console.log("some data");
    //     // console.log("this.marks now", this.marks);
    //     // return this.marks&&insureMarks.map((store, i) => {
    //     let markList = [];
    //     return this.props.locations.map((store, i) => {
    //         // let mesObj = insureMes&&this.messages[i];
    //         let mesObj = this.props.messages[i];
    //         let mes = "id: " + mesObj["id"] + "\n" + "battery: "+mesObj["battery"] + "\n"+"serial number: " + mesObj["sn"];
    //         console.log(mes);
    //         return <Marker key={i} id={i} position={{
    //             lat: store.latitude,
    //             lng: store.longitude,
    //         }}
    //             onClick={this.onMarkerClick} 
    //             name={mes}
    //             />
    //     });
    // }

    drawMarker = () => {
        // console.log("some data");
        // console.log("this.marks now", this.marks);
        // return this.marks&&insureMarks.map((store, i) => {
        let markList = [];
        this.props.locations.map((store, i) => {
            // let mesObj = insureMes&&this.messages[i];
            let mesObj = this.props.messages[i];
            let mes = "id: " + mesObj["id"] + "\n" + "battery: " + mesObj["battery"] + "\n" + "serial number: " + mesObj["sn"];
            console.log(mes);
            markList.push(<Marker key={i} id={i} position={{
                lat: store.latitude,
                lng: store.longitude,
            }}
                onClick={this.onMarkerClick}
                name={mes}
            />)
        });
        console.log(markList);
        return markList;
    }

    render() {
        if (!this.props.google) {
            return <div>Loading...</div>;
        }

        return (
            <div className="map-container">
                <Map 
                    style={{}} 
                    google={this.props.google} 
                    zoom={20}
                    centerAroundCurrentLocation={true}
                >
                    {/* <Marker
                        onClick={this.onMarkerClick}
                        // icon={{
                        //     url: "/img/icon.svg",
                        //     anchor: new google.maps.Point(32, 32),
                        //     scaledSize: new google.maps.Size(64, 64)
                        // }}
                        name={"Current location"}
                    /> */}
                    {this.drawMarker().map((item) => {
                        return item
                    })}
                    <InfoWindow
                        marker={this.state.activeMarker}
                        visible={this.state.showingInfoWindow}
                    >
                        <div>
                            <h1>{this.state.selectedPlace.name}</h1>
                        </div>
                    </InfoWindow>
                </Map>
            </div>
        );
    }
}
export default GoogleApiWrapper({
    apiKey: "AIzaSyDeypXv1R31N6-IxBnr4xbuo2UtUELwnUo",
    v: "3.30"
})(TraceMap);
