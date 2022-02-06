import java.util.Scanner;
import java.util.List;

public class DaysOfTheYear{

    //Here are some constants:
    private static final String MONTH = "month";
    private static final String DAY = "day";

    //Lists of months with 31 or 30 days to use in isARealDate method
    private static final List<Integer> LIST_MONTHS_31_DAYS = List.of(1, 3, 5, 7, 8, 10, 12);
    private static final List<Integer> LIST_MONTHS_30_DAYS = List.of(4, 6, 9, 11);

    public static void main(String[] args){

        //Variable to hold which player's turn it is
        int player = 1;

        //Variable to hold how many goes have been played
        int playCounter = 1;

        //Variable to hold if player would like to change the day or the month
        String dayOrMonthAnswer = DAY;

        //Variables to hold date
        int dayNumber = 1;
        int monthNumber = 1;

        //Change start date if valid arguments supplied from command line
        if ((args.length == 2) && isInt(args[0]) && isInt(args[1])){
            int dayNumberArgs = Integer.parseInt(args[0]);
            int monthNumberArgs = Integer.parseInt(args[1]);
            if (isARealDate(dayNumberArgs, monthNumberArgs)){
                dayNumber = dayNumberArgs;
                monthNumber = monthNumberArgs;
            }
        }

        Scanner in = new Scanner(System.in);

        //A while loop that won't end until 31 dec reached
        while (!(dayNumber == 31 && monthNumber == 12)) {
 
            player = whichPlayer(playCounter);
            printStartingOutput(player, dayNumber, monthNumber);
            dayOrMonthAnswer = safeNext(in);

            //Check validity of dayOrMonthAnswer, while invalid ask again
            while (!(isDayMonthAnswerValid(dayOrMonthAnswer, dayNumber, monthNumber))){
                printInvalidInputError();
                printDayOrMonthQuestion();
                dayOrMonthAnswer = safeNext(in);
            }

            //If input is valid player can change the day or month
            if (dayOrMonthAnswer.equals(DAY)) {
                dayNumber = verifyDayOrMonthValue(dayOrMonthAnswer, dayNumber, monthNumber, in, true);
                playCounter++;
            } else if (dayOrMonthAnswer.equals(MONTH)){
                monthNumber = verifyDayOrMonthValue(dayOrMonthAnswer, dayNumber, monthNumber, in, false);
                playCounter++;
            } else {
                printInvalidInputError();
            }
            System.out.println();
        }
        in.close();

        //The playcounter will increment even after one of the players wins, so adjust accordingly. 
        playCounter--;
        player = whichPlayer(playCounter);

        printCurrentDate(numStringSuffix(dayNumber), monthNumToString(monthNumber));
        System.out.println("Player " + player + " is the winner of the game!");
    }

    //Method to check validity of answer to "would you like to increase the day or month"
    private static boolean isDayMonthAnswerValid(String dayOrMonthAnswer, int dayNumber, int monthNumber){
        if (!((dayOrMonthAnswer.equals(DAY)) || (dayOrMonthAnswer.equals(MONTH)))){
            return false;
        } else if ((dayOrMonthAnswer.equals(DAY) && !isARealDate(dayNumber + 1, monthNumber))){
            return false; 
        } else { 
            return !(dayOrMonthAnswer.equals(MONTH) && monthNumber == 12);
        }
    }

    //Method to print initial messages/ questions
    private static void printStartingOutput(int player, int dayNumber, int monthNumber) {
        printCurrentDate(numStringSuffix(dayNumber), monthNumToString(monthNumber));
        System.out.println("It is Player " + player + "'s Turn!"); 
        printDayOrMonthQuestion();
    }

    //Method to print invalid input error message
    private static void printInvalidInputError(){
        System.out.println("Input invalid, please try again!");
    }

    //Method to print choose day or month question
    private static void printDayOrMonthQuestion(){
        System.out.print("Do you want to increase the day or the month? (day or month): ");
    }

    //Method to ask user to choose the day or month to increase to
    private static void printIncreaseDayOrMonthToWhat(String dayOrMonthAnswer){
        System.out.print("Which " + dayOrMonthAnswer + " do you want to pick: ");
    }

    //Method to print current date
    private static void printCurrentDate(String day, String month){
        System.out.println("The current date is: " + day + " of " + month);
    }

    //Method to decide which player's turn it is based on playCounter
    private static int whichPlayer(int counter){
        if (counter % 2 == 1){
            return 1;
        } else { 
            return 2;
        }
    }

    //Method to check that the input is a valid date and is increasing, if not asks for new input
    private static int verifyDayOrMonthValue(String dayOrMonthAnswer, int dayNumber, int monthNumber, Scanner in, boolean isDay) {
        int newNumber = evaluateIsAnInt(dayOrMonthAnswer, in);
        while (isNewNumberValid(dayNumber, monthNumber, newNumber, isDay)){
            printInvalidInputError();
            newNumber = evaluateIsAnInt(dayOrMonthAnswer, in);
        }
        return newNumber;
    }

    //Method is used by verifyDayOrMonthValue
    private static boolean isNewNumberValid(int dayNumber, int monthNumber, int newNumber, boolean isDay) {
        if (isDay) {
            return (!isARealDate(newNumber, monthNumber)) || (newNumber <= dayNumber);
        } else {
            return (!isARealDate(dayNumber, newNumber)) || (newNumber <= monthNumber);
        }
    }

    //Method to check if date exists
    private static boolean isARealDate(int day, int month){
        if (LIST_MONTHS_31_DAYS.contains(month) && day > 0 && day <= 31){
                return true;
        } else if (LIST_MONTHS_30_DAYS.contains(month) && day > 0 && day <= 30){
                return true;
        } else {
            return (month == 2 && day > 0 && day <= 28);
        }
    }

    //Method to evaluate scanner input and make sure it is an int, if so converts string to int and returns it, if not ask for new
    private static int evaluateIsAnInt(String dayOrMonthAnswer, Scanner in){
        printIncreaseDayOrMonthToWhat(dayOrMonthAnswer);
        while (!in.hasNextInt()){
            printInvalidInputError();
            printIncreaseDayOrMonthToWhat(dayOrMonthAnswer);
            safeNext(in);
        }
        int intAnswer = Integer.parseInt(safeNext(in));
        return intAnswer;
    }

    //Method to safely take scanner input (in.nextLine() after each input to clear the scanner buffer), and put in lowerCase
    private static String safeNext(Scanner in){
        String answer = in.next();
        in.nextLine();
        return answer.toLowerCase();
    }

    //Method to convert monthNumber to Month String
    private static String monthNumToString(int month){
        String monthString = "";
        switch (month) {
            case 1:
                monthString = "January";
                break;
            case 2:
                monthString = "February";
                break;
            case 3:
                monthString = "March";
                break;
            case 4:
                monthString = "April";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "June";
                break;
            case 7:
                monthString = "July";
                break;
            case 8:
                monthString = "August";
                break;
            case 9:
                monthString = "September";
                break;
            case 10:
                monthString = "October";
                break;
            case 11:
                monthString = "November";
                break;
            case 12:
                monthString = "December";
                break;
            default: 
                //runtime exception
            }
                return monthString;
    }

    //Method to convert a number to a string + its suffix (e.g. 1 goes to 1st)
    private static String numStringSuffix(int day){
        int j = day % 10;
        int k = day % 100;
        if (j == 1 && k != 11){
            return day + "st";
        } else if (j == 2 && k != 12){
            return day + "nd";
        } else if (j == 3 && k != 13){
            return day + "rd";
        } else {
            return day + "th";
        }
    }

    //Method used to confirm if arg supplied at command line is an integer
    private static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
