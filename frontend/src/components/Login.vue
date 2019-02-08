<template>
    <b-container fluid class="Login">
        <b-row class="justify-content-md-center">
            <b-col cols="4">
                <b-input-group size="md">
                    <b-input-group-text slot="prepend" class="input-group-text">
                        <div  class="input-group-prepend">
                            Login:
                        </div>
                    </b-input-group-text>
                    <b-form-input type="text" required v-model.trim="username"></b-form-input>
                </b-input-group>
            </b-col>
        </b-row>
        <b-row class="justify-content-md-center">
            <b-col cols="4">
                <b-input-group size="md">
                    <b-input-group-text slot="prepend" class="input-group-text">
                        <div  class="input-group-prepend">
                            Password:
                        </div>
                    </b-input-group-text>
                    <b-form-input type="password" required v-model.trim="password"></b-form-input>
                </b-input-group>
            </b-col>
        </b-row>
        <b-row class="justify-content-md-center mt-2">
            <b-btn variant="primary" :disabled="username === '' || password === ''" v-on:click="tryLoggingIn">Login</b-btn>
        </b-row>
        <b-row class="justify-content-md-center">
            <b-alert variant="danger"
                     dismissible
                     :show="showDismissibleAlert"
                     @dismissed="showDismissibleAlert=false">
                {{this.dismissibleAlertMessage}}
            </b-alert>
        </b-row>



    </b-container>
</template>

<script>
    import {AXIOS} from "./http-common";
    import {mapState, mapMutations, mapActions} from 'vuex';
    export default {
        name: 'Login',
        data () {
            return {
                msg: 'HowTo call REST-Services:',
                response: [],
                errors: [],
                username: "",
                password: "",
                showDismissibleAlert: false,
                dismissibleAlertMessage: ""
            }
        },
        computed: {
            ...mapState(['storedUsername', 'storedPassword'])
        },
        methods: {
            ...mapMutations([
                'setUsername', 'setPassword'
            ]),
            tryLoggingIn () {
                AXIOS.get('/checkuser', {
                    auth: {
                        username: this.username,
                        password: this.password
                    }})
                    .then(response => {
                        console.log(response);
                       if (response.status === 200) {
                           console.log("Dobrze");
                           this.setUsername(this.username);
                           this.setPassword(this.password);
                           this.$router.push({name: "Service"})
                       } else {
                           console.log("Źle");
                           this.$router.go(0);
                       }
                    })
                    .catch(e => {
                        console.log(e.response);
                        if (e.response.status === 401) {
                            console.log("Źle");
                            //this.$router.go(0);
                            this.dismissibleAlertMessage = e.response.data;
                            this.showDismissibleAlert = true;

                        }
                        //this.errors.push(e);
                    })
            }
        }
    }

</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

    .input-group-prepend {
        flex: 0 0 30%;
    }

    .input-group-text {
        width: 100%;
    }

    h1, h2 {
        font-weight: normal;
    }

    ul {
        list-style-type: none;
        padding: 0;
    }

    li {
        display: inline-block;
        margin: 0 10px;
    }

    a {
        color: #42b983;
    }
</style>
