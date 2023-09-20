
import { ajaxMethod } from "./requests.js";

export const apiHandler = async(option, obj) => {
    return new Promise((resolve, reject) => {
        let request = { url: option.url, method: option.method };
        if(obj && obj.data){
            request["body"] = obj.body;
        }
        console.log("requestBody in apiHandler", request);  //request in apis.js
        ajaxMethod(request).then((response) => {
            // console.log("check response");
            // console.log(response);
            if ( response ) {
                const {code, msg, data} = response.data;
                if ( code === 200 ) {
                    resolve(data);
                    //console.log(data);
                }
                else {
                    reject(msg);
                }
            }
        });
    });
};