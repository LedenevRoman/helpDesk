const RequestConstructor = {
    async createAuthorizedRequest(url, method, token, state) {
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify(state)
        })
        return await response.json();
    },

    async createAuthenticationRequest(url, state) {
        return await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(state)
        })
    }
}

export default RequestConstructor;