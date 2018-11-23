# Date: January 22, 2018
# Author: Edward Tang
# Purpose: This program is designed to receive an input text file to read, arrange, display and write its contents
#          in a certain width and justification.
# Input: Keyboard
# Output: Screen, Console
# ==================================================================================================================

# Date: January 22, 2018
# Author: Edward Tang
# Purpose: This class is intended to be used to read, arrange, display and write the contents of an input file
#          based on a width and justification.
# Field Data:
#   wordsList - A list holding the words of the input file
#   docList - A list holding the lines of the justified text
#   strInputFile - A string of the input text file name
#   strOutputFile - A string of the output text file name
#   strJustification - A string of either "l", "r" or "c" to determine the justification
#   strWidth - An integer of the max line with (30-90 characters)
# Methods:
#   readFile() - Reads the input file and adds words to wordsList
#   writeFile() - Writes docList into the output file
#   createDoc() - Uses wordsList to create docList based on the justifcation type
#   justifyLeft() - Creates docList in left justification
#   justifyRight() - Creates docList in right justification
#   justifyCentre() - Creates docList in centre justification
#   __str__() - Returns the string form of the docList text
# ==================================================================================================================

class JustifyText:
    def __init__(self,inputFile="input.txt",outputFile="output.txt",justification="l",width=60):
        self.wordsList = []
        self.docList = []
        self.strInputFile = inputFile
        self.strOutputFile = outputFile
        if justification == "l" or justification == "r" or justification == "c":
            self.strJustification = justification
        if width >= 30 and width <= 90:
            self.intWidth = width
        else:
            self.intWidth = 60
        self.readFile()
        self.createDoc()

    # Date: January 22, 2018
    # Author: Edward Tang
    # Purpose: Reads the input file and adds words to wordsList
    # Input: N/A
    # Output: N/A
    # ==========================================================

    def readFile(self):
        inputFile = open(self.strInputFile, "r")
        for line in inputFile:
            tempString = line.strip().split()
            for word in tempString:
                if len(word) > 30:
                    word = word[:30]
                self.wordsList.append(word)
        inputFile.close()

    # Date: January 22, 2018
    # Author: Edward Tang
    # Purpose: Returns the string form of the docList text
    # Input: N/A
    # Output: [STRING] The docList text
    # =====================================================

    def __str__(self):
        string = ""
        for line in self.docList:
            string = string + line + "\n"
        return string

    # Date: January 22, 2018
    # Author: Edward Tang
    # Purpose: Writes docList into the output file
    # Input: N/A
    # Output: N/A
    # =============================================

    def writeFile(self):
        outputFile = open(self.strOutputFile,"w")
        outputFile.write(self.__str__())
        outputFile.close()

    # Date: January 23, 2018
    # Author: Edward Tang
    # Purpose: Uses wordsList to create docList based on the justifcation type
    # Input: N/A
    # Output: N/A
    # =========================================================================
        
    def createDoc(self):
        if self.strJustification == "l":
            self.justifyLeft()
        elif self.strJustification == "r":
            self.justifyRight()
        elif self.strJustification == "c":
            self.justifyCentre()

    # Date: January 24, 2018
    # Author: Edward Tang
    # Purpose: Creates docList in left justification
    # Input: N/A
    # Output: N/A
    # ================================================

    def justifyLeft(self):
        line = ""
        for word in self.wordsList:
            if len(line) == 0:
                line = line + word
            elif len(line) + len(word) + 1 <= self.intWidth:
                line = line + " " + word
            else:
                self.docList.append(line)
                line = word
        if len(line) > 0:
            self.docList.append(line)

    # Date: January 24, 2018
    # Author: Edward Tang
    # Purpose: Creates docList in right justification
    # Input: N/A
    # Output: N/A
    # ================================================

    def justifyRight(self):
        self.justifyLeft()
        for i in range(0,len(self.docList)):
            gap = self.intWidth - len(self.docList[i])
            self.docList[i] = gap * " " + self.docList[i]

    # Date: January 24, 2018
    # Author: Edward Tang
    # Purpose: Creates docList in centre justification
    # Input: N/A
    # Output: N/A
    # =================================================

    def justifyCentre(self):
        self.justifyLeft()
        for i in range(0,len(self.docList)):
            gap = (self.intWidth - len(self.docList[i])) // 2
            self.docList[i] = gap * " " + self.docList[i]

# Date: November 10, 2017
# Author: Edward Tang
# Purpose: This method is designed to ask the user for an integer and perform checks to make sure the integer
#          is within a certain range.
# Input: Keyboard, [STRING] The input prompt phrase, [STRING] the lowest possible value and the highest 
#        possible value
# Output: Console/Screen, [INTEGER] The user-inputted number
# ============================================================================================================= 

def getValidInteger(prompt="Enter an integer", low = "", high = ""):
    if low.isdigit() and high.isdigit():
        low = int(low)
        high = int(high)
        intUserInput = low - 1
        while intUserInput < low or intUserInput > high:
            strUserInput = ""
            while not strUserInput.isdigit():
                strUserInput = input(prompt + " between " + str(low) + " and " + str(high) + ": ")
                print()
                if not strUserInput.isdigit():
                    print("[ERROR] You did not enter a valid integer! Please try again.")
                    print()
            intUserInput = int(strUserInput)
            if intUserInput < low or intUserInput > high:
                print("[ERROR] You did not enter an integer between " + str(low) + " and " + str(high) + ". Please try again.")
                print()
        return intUserInput
    elif low.isdigit() and not high.isdigit():
        low = int(low)
        intUserInput = low - 1
        while intUserInput < low:
            strUserInput = ""
            while not strUserInput.isdigit():
                strUserInput = input(prompt + " of at least " + str(low) + ": ")
                print()
                if not strUserInput.isdigit():
                    print("[ERROR] You did not enter a valid integer! Please try again.")
                    print()
            intUserInput = int(strUserInput)
            if intUserInput < low:
                print("[ERROR] You did not enter an integer of at least " + str(low) + ". Please try again.")
                print()
        return intUserInput

# MAIN CODE

restart = ""
while restart != "stop":
    width = getValidInteger("Enter a width","30","90")
    justification = input("Enter 'l' (left), 'r' (right) or 'c' (centre) for the justification: ")
    while not (justification == "l" or justification == "r" or justification == "c" or justification == "f"):
        justification = input("Enter 'l' (left), 'r' (right) ot 'c' (centre)for the justification: ")
    print()

    text = JustifyText(inputFile="input.txt",outputFile="output.txt",justification=justification,width=width)
    
    print(text)
    text.writeFile()
    
    restart = input("Enter anything to try a differnt justification, or enter 'stop' to stop: ")
    print()

