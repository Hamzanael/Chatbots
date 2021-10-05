<template>
  <div class="medium-12 column login">
    <div class="text-center medium-12 login__hero align-self-top">
      <img
          alt="Test"
          class="hero__logo"
          src="../../assets/logo.svg"
          style="margin: 0 auto"
      />
      <h2 class="hero__title">
        Login to Mokhatbat
      </h2>
    </div>
    <div class="row align-center">
      <div class="small-12 medium-4 column">
        <form class="login-box column align-self-top">
          <div class="column log-in-form">
            <label>
              Log In
              <input
                  data-testid="email_input"
                  placeholder="Enter Your email"
                  type="text"
              />
            </label>
            <label>
              Password
              <input
                  data-testid="password_input"
                  placeholder="enter password"
                  type="password"
              />
            </label>
            <LoginButton button-class='large expanded' buttonText='Login'/>
          </div>
        </form>
        <div class="column text-center sigin__footer">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import LoginButton from "./LoginButton.vue";

export default {
  name: 'login'
  ,
  components: {
    LoginButton
  }
}
</script>

<style lang="scss">
@import "../../assets/defualt_css/scss/helper_widgets/scss/forms";
</style>