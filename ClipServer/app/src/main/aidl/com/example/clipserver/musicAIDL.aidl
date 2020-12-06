// musicAIDL.aidl
package com.example.clipserver;

// Declare any non-default types here with import statements

interface musicAIDL {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String[] getURL(); // method sends the url to download music
    boolean playMusic(int song); // plays music of givne index
    boolean pauseMusic(int song); // pauses music
    boolean resumeMusic(int song); // resumes music
    boolean stopMusic(int song); // stops music
}
