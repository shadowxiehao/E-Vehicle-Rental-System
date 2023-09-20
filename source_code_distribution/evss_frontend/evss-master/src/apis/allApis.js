import { apiHandler } from "../utils/utils.js"

export const getAllData = () => {
    let url = "/vehicle/find";
    return apiHandler({
        url: url,
        method: "post",
    });
};

export const updateNewStatus = (obj) => {
    let url = "/vehicle/status" + "?sn=" +obj.sn + "&status=" + obj.status;
    return apiHandler(
    {
        url:url,
        method:"post",
    },
        // obj,
    )
};

export const updateNewLocation = (obj) => {
    //http://127.0.0.1/vehicle/location?sn=AABBCC445567&latitude=55.8731765&longitude=-4.2935585 
    let url = "/vehicle/location" + "?sn=" +obj.sn + "&latitude=" + obj.lat + "&longitude=" + obj.lon;
    return apiHandler(
    {
        url: url,
        method: "post",
    },
        // obj,
    );
};

export const updateNewMaintainance = (obj) => {
    let url = "/vehicle/maintain" + "?sn=" +obj.sn + "&email=test@operator.com";
    return apiHandler(
        {
            url: url,
            method: "post",
        },
            // obj,
        );
}

export const updateBattery = (obj) => {
    let url = "/vehicle/charge" + "?sn=" +obj.sn + "&charge=" + obj.battery;
    return apiHandler(
        {
            url: url,
            method: "post",
        },
            // obj,
        );
}

export const addNewVehicle = (obj) => {
    let url = "/vehicle/add" + "?sn=" +obj.sn + "&type=" + obj.type;
    return apiHandler(
        {
            url: url,
            method: "post",
        },
            // obj,
        );
}

export const userCheck = (obj) => {
    let url = "/user/login" + "?email=" + obj.id + "&password=" + obj.password;
    return apiHandler(
        {
            url: url,
            method: "post",
        },
            // obj,
        );
}