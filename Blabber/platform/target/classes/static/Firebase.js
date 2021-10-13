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
        sendVerificationEmail();
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

function sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent


                                    // after email is sent just logout the user and finish this activity
                                    FirebaseAuth.getInstance().signOut();
                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    finish();
                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do

                                    //restart this activity
                                    overridePendingTransition(0, 0);
                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(getIntent());

                        }
                    }
                });
    }

    function checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
            // user is verified, so you can finish this activity or send user to activity which you want.
            finish();
            Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            FirebaseAuth.getInstance().signOut();

            //restart this activity

        }
    }