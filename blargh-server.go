package main

import (
    "container/list"
    "encoding/json"
    "fmt"
    "net/http"
    "sync"
)

//Set up them http handlers
func main(){
        messages.Map = make(map[string]chat_msg_buffer_struct)
        http.HandleFunc("/chat", chatHandler)
        http.HandleFunc("/poll", pollHandler)
        http.ListenAndServe(":8080", nil)
}


//I implemented a list with a lock instead of database! time constraints!
type messages_struct struct {
        Mu sync.RWMutex
        Map map[string]chat_msg_buffer_struct
}

var messages messages_struct

func addMessage(msg chat_msg_struct){
        messages.Mu.Lock()
        defer messages.Mu.Unlock()

        msgs := messages.Map[msg.UsernameTo].Msgs
        if(msgs == nil){
            msgs = list.New()
        }

        msgs.PushFront(msg)
        if msgs.Len()>15{
                msgs.Remove(msgs.Back())
        }
}

//Recieves chat_msg_struct from html and stores it
func chatHandler(w http.ResponseWriter, r *http.Request){
    decoder := json.NewDecoder(r.Body)
    var m chat_msg_struct
    err := decoder.Decode(&m)
    if err != nil {
            fmt.Println(err)
            http.Error(w,err.Error(), http.StatusBadRequest)
            return;
    }
    addMessage(m)
}

//recieves a chat_msg_req_struct from html, responds with a chat_msgs_struct
func pollHandler(w http.ResponseWriter, r *http.Request){
    decoder := json.NewDecoder(r.Body)
    var m chat_msg_req_struct
    err := decoder.Decode(&m)
    if err != nil {
            fmt.Println(err)
            http.Error(w,err.Error(), http.StatusBadRequest)
            return;
    }

    enc := json.NewEncoder(w)
    ms := getMessages(messages.Map[m.Username].Msgs)

    if(ms != nil){
        enc.Encode(&ms)
    }
}

func getMessages(msgs *list.List) []chat_msg_struct{
        var ms []chat_msg_struct

        messages.Mu.RLock()
        defer messages.Mu.RUnlock()
        
        for i := msgs.Front(); i != nil; i = i.Next(){

            ms = append(ms, i.Value.(chat_msg_struct))
        }

        return ms
}

type chat_msg_buffer_struct struct {
    Msgs *list.List
}

//JSON structs
type chat_msg_struct struct {
    Msg []byte
    UsernameTo string
    UsernameFrom string
}

type chat_msgs_struct struct {
    Msgs []chat_msg_struct
}
type chat_msg_req_struct struct {
    Username string
}