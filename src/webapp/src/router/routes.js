import Login from "../components/login/Login.vue";
import Welcome from "../components/Welcome.vue";


const routes = [
    {
        name: "Login",
        path: "/",
        component: Login
    },
    {
        name: "Welcome",
        path: "/welcome",
        component: Welcome
    }

]
export default routes