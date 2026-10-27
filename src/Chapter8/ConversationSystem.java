package Chapter8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ConversationSystem {
	public static ArrayList<String> variableNames = new ArrayList<>();
	public static ArrayList<String> variableValues = new ArrayList<>();
	private static ArrayList<Topic> topics;

	public static Topic findTopicByName(String name) {
		for (Topic t : topics)
			if (t.getTitle().equals(name))
				return t;
		System.err.println("Topic named " + name + " not found");
		return null;
	}

	public static void main(String[] args) throws FileNotFoundException {

		topics = readConversation("testobj1.txt");
		runConversation();

	}

	public static String getContentText(String option) {
		int pos = option.indexOf("->");
		if (pos == -1) {
			return option.trim();
		}
		return option.substring(0, pos);
	}

	public static String getTopicName(String option) {
		int pos = option.indexOf("->");
		if (pos == -1) {
			return option.trim();
		}
		return option.substring(pos + 2).trim();
	}

	// set the variable name and value.
	public static void setVariable(String line) {
		String command = line.substring(5, line.length() - 2).trim();
		int pos = command.indexOf("=");
		String name = command.substring(0, pos).trim();
		String value = command.substring(pos + 1).trim();
		int variablePos = variableNames.indexOf(name);
		if (variablePos != -1) {
			variableValues.set(variablePos, value);
		} else {
			variableNames.add(name);
			variableValues.add(value);
		}
	}

	// get value of the variable by name, if not found, return empty string
	public static String getVariable(String name) {
		int pos = variableNames.indexOf(name);
		if (pos == -1) {
			return "";
		} else {
			return variableValues.get(pos);
		}
	}

	// check the condition of the variable, if it is true, return true, else return
	// false
	public static boolean checkCondition(String condition) {
		int pos = condition.indexOf("=");
		String name = condition.substring(0, pos).trim();
		String value = condition.substring(pos + 1).trim();
		String actualValue = getVariable(name);
		return actualValue.equals(value);
	}

	public static void runConversation() {
		Scanner userInput = new Scanner(System.in);
		Topic current = findTopicByName("Hello");
		while (true) {
			boolean showContent = true;

			// displays content and options
			for (String content : current.getContent()) {
				if (content.startsWith("<<if ")) {
					String condition = content.substring(5, content.length() - 2).trim();

					showContent = checkCondition(condition);
				} else if (content.equals("<<endif>>")) {
					showContent = true;
				} else if (showContent) {
					if (content.startsWith("<<set ")) {
						setVariable(content);
					} else {
						content = replaceVariables(content);
						System.out.println(content);
					}
				}
			}

			// user options
			if (!current.hasOptions())
				break;
			for (int i = 0; i < current.getNumberOfOptions(); i++) {
				System.out.println(i + ": " + getContentText(current.getOption(i)));
			}

			// get user choice
			String input = userInput.next();
			if (!input.matches("\\d*")) {
				System.out.println("Invalid input");
				continue;
			}
			int choice = Integer.parseInt(input);
			// check for valid choice
			if (choice >= 0 && choice < current.getNumberOfOptions()) {
				// gets the next topic
				String nextTopicName = getTopicName(current.getOption(choice));
				current = findTopicByName(nextTopicName);
			} else {
				System.out.println("Invalid choice");
			}
		}
	}

	private static String replaceVariables(String content) {
		int start = content.indexOf("<<get ");
		if (start == -1) {
			return content;
		}
		int end = content.indexOf(">>", start);
		if (end == -1) {
			return content;
		}
		String name = content.substring(start + 6, end);
		String value = getVariable(name);
		String command = content.substring(start, end + 2);
		return content.replace(command, value);
	}

	public static ArrayList<Topic> readConversation(String fileName) throws FileNotFoundException {
		ArrayList<Topic> topics = new ArrayList<>();
		Scanner scan = new Scanner(new File(fileName));
		Topic current = null;
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			if (line.length() < 2)// skip blank lines
				continue;
			if (line.startsWith("::")) {
				if (current != null)
					topics.add(current);
				current = new Topic(line.substring(2));
			} else if (line.startsWith("[[")) {
				current.addOption(line.substring(2, line.length() - 2));
			} else {
				current.addContent(line);
			}
		}
		topics.add(current);
		return topics;
	}
}

class Topic {
	private String title;
	private ArrayList<String> content = new ArrayList<>();
	private ArrayList<String> options = new ArrayList<>();

	public Topic(String title) {
		this.title = title;
	}

	public String getOption(int i) {
		return options.get(i);
	}

	public int getNumberOfOptions() {
		return options.size();
	}

	public boolean hasOptions() {
		return !options.isEmpty();
	}

	public String[] getContent() {
		String[] arr = new String[content.size()];
		content.toArray(arr);
		return arr;
	}

	public Object getTitle() {
		return title;
	}

	public void addContent(String str) {
		content.add(str);
	}

	public void addOption(String str) {
		options.add(str);
	}

	public String toString() {
		return title;
	}
}
