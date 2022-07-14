package leitor;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

public class Leitor {
    public boolean regexValidator(String s, String regex) {  
        return s != null && s.matches(regex);  
    } 

    public boolean isNumeric(String s) {  
        return s != null && s.matches("[0-9]+");  
    }  

    public boolean isDate(String s) {
        return s != null && s.matches("^(\\d\\d)/(\\d\\d)/\\d\\d\\d\\d$");
    }

    public int optionReader(Scanner reader, String textInput, int startValue, int endValue) {
        while (true) {
            System.out.print(textInput);

            String input = reader.nextLine();

            if (isNumeric(input)) {
                String error = "O número deve ser igual ou estar entre " + startValue + " e " + endValue + "!";
                
                try {
                    int num = Integer.parseInt(input);
                
                    if (num >= startValue && num <= endValue) {
                        return num;
                    } else {
                        System.out.println(error);
                    }
                } catch(NumberFormatException ex) {
                    System.out.println(error);
                }
            } else {
                System.out.println("Digite apenas números inteiros entre " + startValue + " e " + endValue + ".");
            }
        }
    }

    public LocalDate dateReader(Scanner reader, String textInput) {
        while (true) {
            System.out.print(textInput);

            String input = reader.nextLine();

            if (isDate(input)) {
                String[] inputSplit = input.split("/");
                
                try {
                    LocalDate date = LocalDate.of(Integer.parseInt(inputSplit[2]), Integer.parseInt(inputSplit[1]), Integer.parseInt(inputSplit[0]));
                    return date;
                } catch(DateTimeException ex) {
                    System.out.println("Data inválida.");
                }
            } else {
                System.out.println("Formato de data inválido.");
            }
        }
    }

    public String regexValidatorReader(Scanner reader, String textInput, String regex) {
        while (true) {
            System.out.print(textInput);

            String input = reader.nextLine();

            if (input != null && input.matches(regex)) {
                return input;
            } else {
                System.out.println("A entrada não está no formato correto.");
            }
        }
    }

    public String stringReader(Scanner reader, String textInput) {
        while (true) {
            System.out.print(textInput);

            String input = reader.nextLine();

            if (input.length() > 0) {
                return input;
            } else {
                System.out.println("A entrada deve conter pelo menos um caractere!");
            }
        }
    }

    public boolean stringBoolReader(Scanner reader, String textInput) {
        while (true) {
            System.out.print(textInput + " (Digite sim ou nao): ");

            String input = reader.nextLine();

            if (input.equalsIgnoreCase("sim")) {
                return true;
            } else if (input.equalsIgnoreCase("nao")) {
                return false;
            } else {
                System.out.println("Digite apenas sim ou nao!");
            }
        }
    }
}
