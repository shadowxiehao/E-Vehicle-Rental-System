import axios from "axios";


export const request = ({url, method = "GET", data}) => {
    let opt = {
        method,
    };
    if (data) {
        opt = {
            ...opt,
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        }
    }

    return fetch(url, opt).then((res) => {
        if ( !res.ok ){
            return Promise.reject(res.status);
        }
        return res.json();
    });
};

export const ajaxMethod = ({url, method, body}) => {
    let promise = axios({url, method, data: body});
    return promise;
}