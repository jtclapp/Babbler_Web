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








//first input delay
!function(n,e){var t,o,i,c=[],f={passive:!0,capture:!0},r=new Date,a="pointerup",u="pointercancel";function p(n,c){t||(t=c,o=n,i=new Date,w(e),s())}function s(){o>=0&&o<i-r&&(c.forEach(function(n){n(o,t)}),c=[])}function l(t){if(t.cancelable){var o=(t.timeStamp>1e12?new Date:performance.now())-t.timeStamp;"pointerdown"==t.type?function(t,o){function i(){p(t,o),r()}function c(){r()}function r(){e(a,i,f),e(u,c,f)}n(a,i,f),n(u,c,f)}(o,t):p(o,t)}}function w(n){["click","mousedown","keydown","touchstart","pointerdown"].forEach(function(e){n(e,l,f)})}w(n),self.perfMetrics=self.perfMetrics||{},self.perfMetrics.onFirstInputDelay=function(n){c.push(n),s()}}(addEventListener,removeEventListener);

import { getPerformance } from "firebase/performance";

// Initialize Performance Monitoring and get a reference to the service
const perf = getPerformance(app);