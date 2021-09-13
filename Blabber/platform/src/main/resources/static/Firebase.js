function login(){

    import { getAuth, signInWithEmailAndPassword } from "firebase/auth";

    var userEmail = document.getElementById("email").value;
    var userPass = document.getElementById("password").value;
    const auth = getAuth();
    signInWithEmailAndPassword(auth, userEmail,userPass)
        .then((userCredential) => {
            // Signed in
            const user = userCredential.user;
            window.location.href = "http://www.w3schools.com";
            // ...
        })
        .catch((error) => {
            const errorCode = error.code;
            const errorMessage = error.message;
            window.alert("Error : " + errorMessage);
        });
}
function create() {
    import { initializeApp } from 'firebase/app';
    const firebaseConfig = {
        apiKey: "AIzaSyCVQQgFthJsTE1kFAUOrffWvVnknm2gSMY",
        authDomain: "conspiracy-theory-chat.firebaseapp.com",
        databaseURL: "https://conspiracy-theory-chat-default-rtdb.firebaseio.com",
        projectId: "conspiracy-theory-chat",
        storageBucket: "conspiracy-theory-chat.appspot.com",
        messagingSenderId: "393777676240",
        appId: "1:393777676240:web:1aa84f66ad1e70c5af83d5",
        measurementId: "G-6JJME5ZDF5"
    };
    const app = initializeApp(firebaseConfig);
    import { getAuth, createUserWithEmailAndPassword } from "firebase/auth";

    var userEmail = document.getElementById("email").value;
    var userPass = document.getElementById("password").value;
    const auth = getAuth();
    createUserWithEmailAndPassword(auth, userEmail,userPass)
        .then((userCredential) => {
            // Signed in
            const user = userCredential.user;
            // ...
        })
        .catch((error) => {
            const errorCode = error.code;
            const errorMessage = error.message;
            // ..
        });
}
function logout(){
    import { getAuth, signOut } from "firebase/auth";

    const auth = getAuth();
    signOut(auth).then(() => {
        // Sign-out successful.
    }).catch((error) => {
        // An error happened.
    });
}
