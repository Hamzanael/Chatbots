<template>
  <div class="medium-12 column login">
    <div class="text-center medium-12 login__hero align-self-top">
      <img alt="Test" class="hero__logo" src="../../assets/logo.svg" style="margin: 0 auto"/>
      <h2 class="hero__title">
        Login to Mokhatbat
      </h2>
    </div>
    <div class="row align-center">
      <div class="small-12 medium-4 column">

        <!--  The form Start from here      -->
        <form
            class="login-box column align-self-top"
            @submit.prevent="submit">
          <div class="column log-in-form">
            <label>Log In
              <input
                  v-model="form.username"
                  data-testid="email_input"
                  placeholder="Enter Your email"
                  type="text"
              />
            </label>
            <label>
              Password
              <input
                  v-model="form.password"
                  data-testid="password_input"
                  placeholder="enter password"
                  type="password"
              />
            </label>
            <LoginButton button-class='large expanded' buttonText='Login'/>
          </div>
        </form>
        <div class="column text-center sign_in__footer">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import LoginButton from "./LoginButton.vue";
import {mapActions} from "vuex"

export default {
  name: 'loginForm',
  data() {
    return {
      form: {
        username: "",
        password: ""
      }
    }
  },
  components: {
    LoginButton
  },
  methods: {
    ...mapActions(["signIn"]),
    submit() {
      this.signIn(this.form)
    }
  }
}
</script>

<style lang="scss">
@import "../../assets/defualt_css/scss/helper_widgets/scss/forms";
</style>