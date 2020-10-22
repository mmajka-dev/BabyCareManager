//package com.mmajka.babycaremanager.data
//
//import android.content.Context
//import android.util.Log
//import android.widget.TextView
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//
//class FirebaseSource(path: String) {
//
//    val instance = FirebaseDatabase.getInstance()
//    val ref = instance.getReference(path)
//
//    //Get child informations
//    fun getChildInfo(name: TextView, age: TextView){
//        ref.orderByChild("child").addValueEventListener(object : ValueEventListener{
//            override fun onCancelled(error: DatabaseError) {
//                Log.e("Firebase error", "${error.code}: ${error.message}")
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                name.text = snapshot.child("name").value.toString()
//                age.text = snapshot.child("age").value.toString()
//            }
//        })
//    }
//
//    //Put child informations
//    fun putChildInfo(name: String, age: String, path: String){
//        ref.child(path).setValue(child(name, age))
//    }
//
//    //Put actions
//    fun putAction(action: BasicActionEntity, path: String){
//        ref.child(path).child("actions").setValue(action)
//    }
//
//    //Get all actions
//    fun getActions(): LiveData<ArrayList<BasicActionEntity>>{
//        var _actions = arrayListOf<BasicActionEntity>()
//        var actions = MutableLiveData<ArrayList<BasicActionEntity>>()
//
//        ref.orderByChild("actions").addValueEventListener(object : ValueEventListener{
//            override fun onCancelled(error: DatabaseError) {
//                Log.e("Firebase error", "${error.code}: ${error.message}")
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                _actions.clear()
//                for(i in snapshot.children){
//                    val item =i.getValue(BasicActionEntity::class.java)
//                    _actions.add(item!!)
//                }
//                actions.postValue(_actions)
//            }
//        })
//        return actions
//    }
//
//    //Get invite tokens
//    fun getTokens(){
//        var tokens = arrayListOf<TokenModel>()
//
//        ref.orderByChild("tokens").addValueEventListener(object : ValueEventListener{
//            override fun onCancelled(error: DatabaseError) {
//                Log.e("Firebase error", "${error.code}: ${error.message}")
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                tokens.clear()
//                for(i in snapshot.children){
//                    val token =i.getValue(TokenModel::class.java)
//                    tokens.add(token!!)
//                }
//            }
//        })
//    }
//
//    fun getIDs(): ArrayList<String>{
//        var childrens = ArrayList<String>()
//        ref.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(error: DatabaseError) {
//                Log.e("Firebase error", "${error.code}: ${error.message}")
//            }
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.hasChildren()){
//                    snapshot.children.forEach {
//                        childrens.add(snapshot.children.toString())
//                    }
//                }
//            }
//        })
//        return childrens
//    }
//}