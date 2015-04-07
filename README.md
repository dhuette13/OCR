# OCR
An Optical Character Recognition Android Application using the Tesseract API.

## Git commands
All of these commands must be run from the root of your project directory.
- Clone the repository: **git clone https://github.com/dhuette13/OCR.git**
  - The address specifed will be saved as a remote. After this you can push and pull without specifying an address for       the github repository
  - Clone will only have to be used once to get an initial copy of the project. After that push and pull are used to         interact with the server.
- Stage your changes: **git add .**
  - This will only add your changes to a temporary staging area, not to your actual local repository.
  - The . refers the the current working directory, so this command adds all items in your current working directory to the staging area.
  - Changes must be staged before they can be commited
- Commit your changes locally: **git commit -m "Enter commit message"**
  - This will add your changes to your local git repository. But not to github
  - The commit message is necessary for all commits, its just a short description of what you changed.
- Push changes to github: **git push**
  - Merges the changes in your local repository to the github repository 
  - Must enter username and password for github
  - If someone has pushed since your last pull, you will have to pull the new additions before you can push your changes.
- Pull new changes to local repository: **git pull**
  - Fetches changes from github and merges them with your local copy of the project. 
  - If you have made local changes to the project, you must commit them locally before you can pull new changes.
- For your first use of git, it will require you to enter a name and email for identifying changes:
  - **git config --global user.name "John Doe"**
  - **git config --global user.email johndoe@example.com**
  
##SRA list:

    - Objectives: Pragya
    - Context Diagram: Karen
    - Software Processes and Infrastructure: Celine
    - Assumptions and Constraints: Karen
    - Delivery Schedule: Daniel
    - Appendix: Daniel


##Requirements:

    1) Start Screen: Karen
    2) Upload File Chooser: Karen
    3) Camera: Celine
    4) Image Preprocessing UI: Daniel
    5) Save to Format: Pragya
    6) Crop: Daniel
    7) Rotate: Daniel
    8) Undo / Cancel / Done (For Image Prepossessing): Pragya
    9) Text Preview
    10) Help Button: Celine

##Dependencies:

    - Android SDK 22
        - Android libraries
        - adb
    - Android NDK
        - ndk-build
        - android update
    - tess-two library
        - Android bindings for tesseract library
    - tessdata
        - Trained Language files
    - Google API 22
    
##Setup:
  
    - Download and install android sdk and ndk
      - Will need commands from these packages.
    - git clone https://github.com/dhuette13/OCR.git
    - **Build tess two**
      - cd OCR/tess-two
      - ndk-build
      - android update project --path .
      - ant release
    - **Setup Eclipse**
      - Install ADT
      - Specify path to SDK and NDK in Preferences
      - Import existing Android Projects
        - tess-two
        - tess-two-test (Test Suite)
        - OCRApp
      - Set tess-two as library
        - tess-two-test
        - OCRApp
  
##Things To Do:

    - Karen 
      - Start Screen
      - Camera
      - File Upload
    - Daniel 
      - Image Preprocessing
    - Celine
      - Conversion
    - Pragya 
      - Text Preview
      - Save to Format
      - Help (screens that need button: Start Screen, Image Preprocessing, Save to Format)

##To Remember:

   - Color Concept: white prevalent, blue-green accents
   - File Upload and Save to Format can use FileChooser
   - Save to Format will probably require a PDF Library
   - Help "screen"/"pop-up" shows FAQ and general tips & tricks to help user understand the current screen and what to do (OK button)
   - Conversion needs buffer screen
   - Text Preview allows scrolling (buttons: Cancel and OK)
   - Save to Format: 3 main buttons (TXT, PDF, Cancel) along with a Help Button
   - Image Preprocessing: 6 main buttons, help button, and nodes to allow user to select the area of text they want to convert
