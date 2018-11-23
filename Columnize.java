/*

Author: Edward Tang
Date: October 17, 2018
Purpose: This program is designed to receive and output text in a newspaper format, including with files.

*/

// The "Columnize" class.
import java.awt.*;
import hsa.*;
import java.util.*;

public class Columnize
{
    static Console c;           // The output console

    public static void main (String[] args)
    {
	c = new Console (48, 215, "Columnize");

	String restart;

	do
	{
	    c.print ("Enter the justification type (l, c, r or f for left, centre, right or full respectively): ");
	    String justification = c.readString ();
	    while (!justification.equals ("l") && !justification.equals ("c") && !justification.equals ("r") && !justification.equals ("f"))
	    {
		c.print ("[ERROR] You must enter \"l\", \"c\", \"r\" or \"f\": ");
		justification = c.readString ();
	    }

	    int columns = readBoundInt (1, 4, "Enter the number of columns (integer from 1 to 4): ", "[ERROR] You must enter an integer from 1 to 4: ");
	    int width = readBoundInt (20, 50, "Enter the column width (integer from 20 to 50): ", "[ERROR] You must enter an integer from 20 to 50: ");

	    NewsFormat testPaper = new NewsFormat (justification, columns, width);

	    c.println ();
	    testPaper.get (c);
	    c.println ("\n" + testPaper.toString ());
	    testPaper.writeFile ();

	    c.println ();
	    c.print ("Enter anything to apply formatting to more text, or enter 'stop' to stop the program: ");
	    restart = c.readString ();
	    c.println ();
	}
	while (!restart.equals ("stop"));
	// Place your program here.  'c' is the output console
    } // main method


    /*

    Author: Edward Tang
    Date: September 13, 2018
    Purpose: This method is designed to request input from the user for an integer value, redo the input request with an error message if the value is not within desired bounds
	     (inclusive range, [OPTIONAL] odd/even), and return a "valid" input.
    Input: [int] The minimum integer value, [int] the maximum integer value, [String] the desired input request message, [String] the desired error message, [int] desired integer
	   parity (DEFAULT impossible parity, AKA no eventual parity check)
    Output: [int] An integer within the desired range, inclusively

    */

    public static int readBoundInt (int intMin, int intMax, String readRequest, String error, int parity)
    {
	c.print (readRequest);
	int num = c.readInt ();
	while (num < intMin || num > intMax || (num % 2 == 0 && parity == 1) || (num % 2 != 0 && parity == 0))
	{
	    c.print (error);
	    num = c.readInt ();
	}
	return num;
    }


    /*

    Author: Edward Tang
    Date: September 13, 2018
    Purpose: This is the overloaded version of readBoundInt, meant to exclude parity checks.
    Input: [int] The minimum integer value, [int] the maximum integer value, [String] the desired input request message, [String] the desired error message, [int] desired integer
	   parity (DEFAULT impossible parity, AKA no eventual parity check)
    Output: [int] An integer within the desired range, inclusively

    */

    public static int readBoundInt (int intMin, int intMax, String readRequest, String error)
    {
	return readBoundInt (intMin, intMax, readRequest, error, 3);
    }
} // Columnize class

/*

Author: Edward Tang
Date: October 17, 2018
Purpose: This class is designed to receive and output text in a newspaper format, including with files.
Fields:
    words              The words of the text
    lines              The lines of the text
    justification      The justification type for the text ("l" for left, "c" for centre, "r" for right, "f" for full)
    columns            The number of columns
    width              The character width for each column
    outputAddress      The file address for the output file
Methods:
    constructor
    get                Obtains the text from a text file at a file address input by the user
    addTxtExtension    Adds a ".txt" file extension if it is missing from a given file address
    applyFormatting    Applies justification and column formatting to the text
    toString           Returns the formatted text as a String
    writeFile          Writes the text to an output text file
    justifyLeft        Applies left justification to the text
    justifyRight       Applies right justification to the text
    justifyCentre      Applies centre justification to the text
    justifyFull        Applies full justification to the text
    columnize          Arranges the text into columns
*/

class NewsFormat
{
    String[] words;
    String[] lines;

    String justification;
    int columns;
    int width;

    String outputAddress;

    /*

    Author: Edward Tang
    Date: October 17, 2018
    Purpose: This is the constructor for the NewsFormat class, with a String parameter for the text to be formatted.
    Input: [String] The text to be formatted
    Output: N/A

    */

    public NewsFormat (String newText)
    {
	words = newText.split (" ");
    }


    /*

    Author: Edward Tang
    Date: October 17, 2018
    Purpose: This is the constructor for the NewsFormat class, with no parameters - defaulting to no text.
    Input: N/A
    Output: N/A

    */

    public NewsFormat ()
    {
	this ("");
    }


    /*

    Author: Edward Tang
    Date: October 17, 2018
    Purpose: This is the constructor for the NewsFormat class, with parametres for justification, column amount and column width.
    Input: [String] justification type, [int] column number, [int] column width
    Output: N/A

    */

    public NewsFormat (String justification, int columns, int width)
    {
	this ("");
	this.justification = justification;
	this.columns = columns;
	this.width = width;
    }


    /*

    Author: Edward Tang
    Date: October 20, 2018
    Purpose: This is the clone constructor for the NewsFormat class, with a NewsFormat object parameter.
    Input: [NewsFormat] The object to be cloned
    Output: N/A

    */

    public NewsFormat (NewsFormat oldObject)
    {
	this (oldObject.justification, oldObject.columns, oldObject.width);
	outputAddress = oldObject.outputAddress;
	for (int i = 0 ; oldObject.words != null && i < oldObject.words.length ; i++)
	    words [i] = oldObject.words [i];
	for (int i = 0 ; oldObject.lines != null && i < oldObject.lines.length ; i++)
	    lines [i] = oldObject.lines [i];
    }


    /*

    Author: Edward Tang
    Date: October 17, 2018
    Purpose: This method is designed to obtain the input text and output text file from the user.
    Input: [Console] The Console for the user to input addresses from
    Output: N/A

    */

    public void get (Console c)
    {
	String text = "";
	c.print ("Enter the text file address for input: ");
	TextInputFile input = new TextInputFile (addExtension (c.readLine (), ".txt"));
	while (!input.eof ())
	    text += input.readString () + " ";
	input.close ();
	words = text.split (" ");
	c.print ("Enter the text file address for output: ");
	outputAddress = addExtension (c.readLine (), ".txt");
    }


    /*

    Author: Edward Tang
    Date: October 20, 2018
    Purpose: This method is designed to add a file extension to an address without it (this is done assuming the rest of the file address is valid).
    Input: [String] The file address
    Output: [String] The file address with an added extension

    */

    public String addExtension (String address, String extension)
    {
	if (address.indexOf (extension, 0) == -1)
	    address += extension;
	return address;
    }


    /*

    Author: Edward Tang
    Date: October 19, 2018
    Purpose: This method is designed to apply justification and column formatting to the text.
    Input: N/A
    Output: N/A

    */

    public void applyFormatting ()
    {
	if (justification.equals ("l"))
	    justifyLeft ();
	else if (justification.equals ("c"))
	    justifyCentre ();
	else if (justification.equals ("r"))
	    justifyRight ();
	else
	    justifyFull ();
	columnize ();
    }


    /*

    Author: Edward Tang
    Date: October 17, 2018
    Purpose: This method is designed to return the text as a formatted string.
    Input: N/A
    Output: [String] The text as a formatted string.

    */

    public String toString ()
    {
	applyFormatting ();

	String text = "";
	for (int i = 0 ; i < lines.length ; i++)
	    text += lines [i] + "\n";
	return text;
    }


    /*

    Author: Edward Tang
    Date: October 18, 2018
    Purpose: This method is designed to write the formatted text to the designated output address.
    Input: N/A
    Output: N/A

    */

    public void writeFile ()
    {
	applyFormatting ();

	TextOutputFile output = new TextOutputFile (outputAddress);
	for (int i = 0 ; i < lines.length ; i++)
	    output.println (lines [i]);
	output.close ();
    }


    /*

    Author: Edward Tang
    Date: October 18, 2018
    Purpose: This method is designed to apply left justification to the text.
    Input: N/A
    Output: N/A

    */

    public void justifyLeft ()
    {

	String text = "";
	String line = "";
	for (int i = 0 ; i < words.length ; i++)
	{
	    if (line.length () == 0)
		line += words [i];
	    else if (line.length () + words [i].length () + 1 <= width)
		line += " " + words [i];
	    else
	    {
		for (int space = width - line.length () ; space > 0 ; space--)
		    line += " ";
		text += line + "\n";
		line = words [i];
	    }
	}

	if (line.length () > 0)
	    text += line;
	lines = text.split ("\n");
    }


    /*

    Author: Edward Tang
    Date: October 18, 2018
    Purpose: This method is designed to apply centre justification to the text.
    Input: N/A
    Output: N/A

    */

    public void justifyCentre ()
    {
	justifyLeft ();
	for (int i = 0 ; i < lines.length ; i++)
	{
	    lines [i] = lines [i].trim ();
	    int gap = (width - lines [i].length ()) / 2;
	    String strGap = "";
	    for (int num = 1 ; num <= gap ; num++)
		strGap += " ";
	    lines [i] = strGap + lines [i] + strGap;
	}
    }


    /*

    Author: Edward Tang
    Date: October 18, 2018
    Purpose: This method is designed to apply right justification to the text.
    Input: N/A
    Output: N/A

    */

    public void justifyRight ()
    {
	justifyLeft ();
	for (int i = 0 ; i < lines.length ; i++)
	{
	    lines [i] = lines [i].trim ();
	    String strGap = "";
	    for (int space = width - lines [i].length () ; space > 0 ; space--)
		strGap += " ";
	    lines [i] = strGap + lines [i];
	}
    }


    /*

    Author: Edward Tang
    Date: October 18, 2018
    Purpose: This method is designed to apply full justification to the text.
    Input: N/A
    Output: N/A

    */

    public void justifyFull ()
    {
	justifyLeft ();
	for (int i = 0 ; i < lines.length - 1 ; i++)
	{
	    lines [i] = lines [i].trim ();
	    StringBuffer newLine = new StringBuffer (lines [i]);
	    int spaceCount = 2;

	    for (int index = 0 ; newLine.length () < width && newLine.indexOf (" ", 0) != -1 ; index += spaceCount)
	    {
		index = newLine.indexOf (" ", index);

		if (index > -1)
		    newLine.insert (index, " ");
		if (index >= newLine.length ())
		{
		    spaceCount++;
		    index = newLine.indexOf (" ", 0);
		}

	    }
	    lines [i] = newLine.toString ();
	}
    }


    /*

    Author: Edward Tang
    Date: October 18, 2018
    Purpose: This method is designed to split the text into columns.
    Input: N/A
    Output: N/A

    */

    public void columnize ()
    {
	if (columns > 1)
	{
	    int columnHeight = (lines.length + 1) / columns;
	    if (lines.length > columnHeight * columns)
		columnHeight++;
	    int maxColumnHeight = columnHeight;
	    for (int i = 0 ; i <= columnHeight - 1 ; i++)
	    {
		int lineNum = i + maxColumnHeight;
		for (int columnNum = 1 ; columnNum < columns && lineNum < lines.length ; columnNum++)
		{
		    if (columnHeight * (columns - columnNum + 1) > lines.length - (columnNum - 1) * columnHeight + columnHeight / 2 && columnHeight > 1)
			columnHeight--;
		    lines [i] += "     " + lines [lineNum];
		    lineNum += columnHeight;
		}
	    }
	    String[] newLines;
	    if (maxColumnHeight > 1)
		newLines = new String [maxColumnHeight];
	    else
		newLines = new String [1];
	    for (int i = 0 ; i < newLines.length ; i++)
	    {
		newLines [i] = lines [i];
	    }
	    lines = newLines;
	}
    }
}
