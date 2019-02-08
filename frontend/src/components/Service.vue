<template>
  <div class="service">
    <h4>You are logged as: {{storedUsername}}</h4>

    <b-table :items="getIotNodes" :fields="fields" ref="table" id="iot_table_id">
      <template slot="show_details" slot-scope="row">
        <!-- we use @click.stop here to prevent emitting of a 'row-clicked' event  -->
        <b-btn size="sm" @click.stop="row.toggleDetails" class="mr-2">
          Edit
        </b-btn>
        <b-btn size="sm" @click.stop="deleteIotNode(row)" class="mr-2">
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
                    Duty 1:
                  </div>
                </b-input-group-text>
                <b-form-input type="text" v-model.trim="row.item.duty1"></b-form-input>
              </b-input-group>
            </b-col>
          </b-row>
          <b-row class="justify-content-md-center">
            <b-col cols="4">
              <b-input-group size="md">
                <b-input-group-text slot="prepend" class="input-group-text">
                  <div  class="input-group-prepend">
                    Duty 2:
                  </div>
                </b-input-group-text>
                <b-form-input type="text" v-model.trim="row.item.duty2"></b-form-input>
              </b-input-group>
            </b-col>
          </b-row>
          <b-row class="justify-content-md-center">
            <b-col cols="4">
              <b-input-group size="md">
                <b-input-group-text slot="prepend" class="input-group-text">
                  <div  class="input-group-prepend">
                    Duty 3:
                  </div>
                </b-input-group-text>
                <b-form-input type="text" v-model.trim="row.item.duty3"></b-form-input>
              </b-input-group>
            </b-col>
          </b-row>
          <b-row class="justify-content-md-center">
            <b-col cols="4">
              <b-form-textarea id="textarea1"
                               v-model="row.item.message"
                               placeholder="Additional message"
                               :rows="3"
                               :max-rows="6">
              </b-form-textarea>
            </b-col>
          </b-row>
          <b-row class="justify-content-md-center mt-2">
            <b-btn variant="primary" v-on:click="edit(row)">Submit changes</b-btn>
          </b-row>

        </b-card>
      </template>

    </b-table>


    <b-btn v-on:click="addIotNode = !addIotNode">Add IotNode</b-btn>
    <b-card v-if="addIotNode">
      <b-row class="justify-content-md-center">
        <b-col cols="4">
          <b-input-group size="md">
            <b-input-group-text slot="prepend" class="input-group-text">
              <div  class="input-group-prepend">
                Duty 1:
              </div>
            </b-input-group-text>
            <b-form-input type="text" v-model.trim="newIotNode.duty1"></b-form-input>
          </b-input-group>
        </b-col>
      </b-row>
      <b-row class="justify-content-md-center">
        <b-col cols="4">
          <b-input-group size="md">
            <b-input-group-text slot="prepend" class="input-group-text">
              <div  class="input-group-prepend">
                Duty 2:
              </div>
            </b-input-group-text>
            <b-form-input type="text" v-model.trim="newIotNode.duty2"></b-form-input>
          </b-input-group>
        </b-col>
      </b-row>
      <b-row class="justify-content-md-center">
        <b-col cols="4">
          <b-input-group size="md">
            <b-input-group-text slot="prepend" class="input-group-text">
              <div  class="input-group-prepend">
                Duty 3:
              </div>
            </b-input-group-text>
            <b-form-input type="text" v-model.trim="newIotNode.duty3"></b-form-input>
          </b-input-group>
        </b-col>
      </b-row>
      <b-row class="justify-content-md-center">
        <b-col cols="4">
          <b-form-textarea id="textarea2"
                           v-model="newIotNode.message"
                           placeholder="Additional message"
                           :rows="3"
                           :max-rows="6">
          </b-form-textarea>
        </b-col>
      </b-row>
      <b-row class="justify-content-md-center mt-1">
        <b-btn variant="primary" v-on:click="createIotNode">Create IotNode</b-btn>
      </b-row>

    </b-card>

  </div>
</template>

<script>
  import {AXIOS} from './http-common'
  import {mapState} from 'vuex'

  export default {
    name: 'service',
    computed: {
      ...mapState(['storedUsername', 'storedPassword'])
    },
    data () {
      return {
        msg: 'HowTo call REST-Services:',
        response: [],
        errors: [],
        fields: [
          {
            key: 'id',
            label: 'Id',
          },
          {
            key: 'duty1',
            label: 'Duty 1',
          },
          {
            key: 'duty2',
            label: 'Duty 2',
          },
          {
            key: 'duty3',
            label: 'Duty 3',
          },
          {
            key: 'message',
            label: 'Message',
          },
          {
            key: 'show_details',
            label: 'Actions',
          }
        ],
        addIotNode: false,
        newIotNode: {}
      }
    },
    methods: {
      getIotNodes (ctx) {
        let promise = AXIOS.get('',{
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
        AXIOS.patch('/' + row.item.id, row.item,{
          auth: {
            username: this.storedUsername,
            password: this.storedPassword
          }});
        row.toggleDetails();
      },
      createIotNode () {
        AXIOS.post('/', this.newIotNode,{
          auth: {
            username: this.storedUsername,
            password: this.storedPassword
          }});
        this.addIotNode = !this.addIotNode;
        setTimeout(this.$refs.table.refresh, 1000);
      },
      deleteIotNode (row) {
        AXIOS.delete('/' + row.item.id,{
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
</style>
