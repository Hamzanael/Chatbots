import {createStore} from "vuex"
import axios from "axios";

const store = createStore({
    state() {
        return {
            token: null,
            user: null
        }
    },
    mutations: {
        setToken(state, token) {
            state.token = token
        },
        resetToken(state) {
            state.token = null
        },
        setUser(state, user) {
            state.user = user
        },
        resetUser(state) {
            state.user = null
        }
    },
    actions: {
        async signIn({dispatch}, credentials) {
            const response = await axios.post('/login', credentials);
            dispatch("attempt", response.headers.token)
        }
        ,
        async attempt({commit}, token) {
            commit("setToken", token)
            try {
                const response = await axios.get('/auth/me', {
                    headers: {
                        'Authorization': "Bearer " + token
                    }
                });
                commit("setUser", response.data)
                console.log(token)
                console.log(this.state.user)
            } catch (unauthorized) {
                console.log(unauthorized)
            }
        }
    },

})
export default store;