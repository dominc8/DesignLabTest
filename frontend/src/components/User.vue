<template>
  <div class="user">
    <h4>You are logged as: {{storedUsername}}</h4>

    <b-table :items="getUsers" :fields="fields" ref="table">
      <template slot="show_details" slot-scope="row">
        <!-- we use @click.stop here to prevent emitting of a 'row-clicked' event  -->
        <b-btn size="sm" @click.stop="row.toggleDetails" class="mr-2">
          Edit
        </b-btn>
        <b-btn size="sm" @click.stop="deleteUser(row)" class="mr-2">
          Delete
        </b-btn>
      </template>
      <template slot="row-details" slot-scope="row">
        <b-card>

          <b-row class="justify-content-md-center">
            <b-col cols="4">
              <b-input-group size="md">
                <b-input-group-text slot="prepend" class="input-group-text">
                  <div  class="input-group-prepend">
                    Username:
                  </div>
                </b-input-group-text>
                <b-form-input type="text" v-model.trim="row.item.username"></b-form-input>
              </b-input-group>
            </b-col>
          </b-row>
          <b-row class="justify-content-md-center">
            <b-col cols="4">
              <b-input-group size="md">
                <b-input-group-text slot="prepend" class="input-group-text">
                  <div  class="input-group-prepend">
                    Authority:
                  </div>
                </b-input-group-text>
                <b-form-select v-model.trim="row.item.authority" :options="authorityOptions"></b-form-select>
              </b-input-group>
            </b-col>
          </b-row>
          <b-row class="justify-content-md-center">
            <b-col cols="4">
              <b-input-group size="md">
                <b-input-group-text slot="prepend" class="input-group-text">
                  <div  class="input-group-prepend">
                    Enabled:
                  </div>
                </b-input-group-text>
                <b-form-select v-model.trim="row.item.enabled" :options="[true, false]"></b-form-select>
              </b-input-group>
            </b-col>
          </b-row>
          <b-row class="justify-content-md-center mt-1 mb-3">
            <b-btn variant="primary" v-on:click="edit(row)">Submit changes</b-btn>
          </b-row>
          <b-row class="justify-content-md-center">
            <b-col cols="4">
              <b-input-group size="md">
                <b-input-group-text slot="prepend" class="input-group-text">
                  <div  class="input-group-prepend">
                    Password:
                  </div>
                </b-input-group-text>
                <b-form-input type="password" v-model.trim="row.item.newPassword"></b-form-input>
              </b-input-group>
            </b-col>
          </b-row>
          <b-row class="justify-content-md-center mt-1">
            <b-btn variant="danger" v-on:click="changePassword(row)">Change password (Be careful!)</b-btn>
          </b-row>

        </b-card>
      </template>

    </b-table>

    <b-btn v-on:click="addUser = !addUser">Add user</b-btn>
    <b-card v-if="addUser">
      <b-row class="justify-content-md-center">
        <b-col cols="4">
          <b-input-group size="md">
            <b-input-group-text slot="prepend" class="input-group-text">
              <div class="input-group-prepend">
                Username:
              </div>
            </b-input-group-text>
            <b-form-input type="text" required v-model.trim="newUser.username"></b-form-input>
          </b-input-group>
        </b-col>
      </b-row>
      <b-row class="justify-content-md-center">
        <b-col cols="4">
          <b-input-group size="md">
            <b-input-group-text slot="prepend" class="input-group-text">
              <div  class="input-group-prepend">
                Authority:
              </div>
            </b-input-group-text>
            <b-form-select required v-model.trim="newUser.authority" :options="authorityOptions"></b-form-select>
          </b-input-group>
        </b-col>
      </b-row>
      <b-row class="justify-content-md-center">
        <b-col cols="4">
          <b-input-group size="md">
            <b-input-group-text slot="prepend" class="input-group-text">
              <div  class="input-group-prepend">
                Enabled:
              </div>
            </b-input-group-text>
            <b-form-select required v-model.trim="newUser.enabled" :options="[true, false]"></b-form-select>
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
            <b-form-input type="password" v-model.trim="newUser.password"></b-form-input>
          </b-input-group>
        </b-col>
      </b-row>
      <b-row class="justify-content-md-center mt-1">
        <b-btn variant="primary" v-on:click="createUser" :disabled="newUser.username === '' || newUser.authority === ''">Create User</b-btn>
      </b-row>

    </b-card>

  </div>
</template>

<script>
  // import axios from 'axios'
  import {AXIOS} from './http-common'
  import {mapState} from 'vuex'

  export default {
    name: 'user',
    computed: {
      ...mapState(['storedUsername', 'storedPassword'])
    },

    data () {
      return {
        response: [],
        errors: [],
        fields: [
          {
            key: 'id',
            label: 'Id',
          },
          {
            key: 'username',
            label: 'Username',
          },
          {
            key: 'authority',
            label: 'Authority',
          },
          {
            key: 'enabled',
            label: 'Enabled',
          },
          {
            key: 'show_details',
            label: 'Actions',
          }
        ],
        authorityOptions: [
          { value: 'ROLE_ADMIN', text: 'Admin' },
          { value: 'ROLE_USER', text: 'User' }
        ],
        addUser: false,
        newUser: {
          username: '',
          password: '',
          authority: '',
          enabled: false
        }
      }
    },
    methods: {
      getUsers(ctx) {
        let promise = AXIOS.get('/user',{
          auth: {
            username: this.storedUsername,
            password: this.storedPassword
          }});
        return promise.then((response) => {
          let items = response.data;
          return(items || [])
        })
      },
      edit (row) {
        let updatedItem = {
          id: row.item.id,
          username: row.item.username,
          authority: row.item.authority,
          enabled: row.item.enabled
        };
        AXIOS.patch('/user/' + row.item.id, updatedItem,{
          auth: {
            username: this.storedUsername,
            password: this.storedPassword
          }});
        row.toggleDetails();
      },
      changePassword (row) {
        let updatedItem = {
          password: row.item.newPassword
        };
        AXIOS.patch('/user/' + row.item.id, updatedItem,{
          auth: {
            username: this.storedUsername,
            password: this.storedPassword
          }});
      },
      createUser () {
        AXIOS.post('/user', this.newUser,{
          auth: {
            username: this.storedUsername,
            password: this.storedPassword
          }});
        this.addUser = !this.addUser;
        setTimeout(this.$refs.table.refresh, 1000);
      },
      deleteUser (row) {
        AXIOS.delete('/user/' + row.item.id,{
          auth: {
            username: this.storedUsername,
            password: this.storedPassword
          }});
        setTimeout(this.$refs.table.refresh, 1000);
      }
    },
    mounted() {
      if (this.storedUsername === '')
        this.$router.push({name: "Login"})
    },
  }

</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
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

  .input-group-prepend {
    flex: 0 0 30%;
  }

  .input-group-text {
    width: 100%;
  }
</style>
