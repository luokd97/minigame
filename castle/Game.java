package castle;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class Game {
	private Room currentRoom;
	private HashMap<String, Room> map = new HashMap<String, Room>();

	private JButton north, east, south, west, build, help;
	private JPanel left, right, cent;
	private JLabel nor, eas, sou, wes, center;

	private JFrame frame;
	private JPanel panel;
	private CheckboxGroup cbg;
	private JTextField text;

	public Game() {
		initMap();
		initView();
		addListener();
		start();
	}

	private void addListener() {
		north.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goRoom("north");
			}
		});
		south.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goRoom("south");
			}
		});
		west.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goRoom("west");
			}
		});
		east.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goRoom("east");
			}
		});
		build.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				buildRoom(currentRoom, text.getText(), cbg.getSelectedCheckbox().getLabel());
			}
		});
		help.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "迷路了吗？你可以点击地图周围的按钮来移动自己的位置\n\n你还可以在当前位置选择一个没有建筑的方向建造新的建筑物。\n\n\t具体操作是：\n\t在四个方向中选择一个合适的方向，然后点击建造，\n\t并在弹出的输入框中为新建筑命名，最后点击确定。", "确定", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	private void initView() {
		frame = new JFrame();

		panel = new JPanel();
		panel.setLayout(new BorderLayout());

		Toolkit kit;
		Dimension screenSize;

		kit = Toolkit.getDefaultToolkit();
		screenSize = kit.getScreenSize();

		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		frame.setSize(screenWidth / 3, screenHeight / 3);
		frame.setLocationByPlatform(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setTitle("城堡扩建计划");
		addComponent();
		frame.add(panel);

		frame.setVisible(true);

	}

	private void addComponent() {
		left = new JPanel();
		right = new JPanel();
		cent = new JPanel();
		text=new JTextField();

		left.setLayout(new BorderLayout());
		cent.setLayout(new BorderLayout());
		right.setLayout(new GridLayout(7, 1));

		panel.setSize(frame.getWidth(), frame.getHeight());
		right.setSize(panel.getWidth(), panel.getHeight());

		north = new JButton("北(N)");
		east = new JButton("东(E)");
		south = new JButton("南(S)");
		west = new JButton("西(W)");
		build = new JButton("建造");
		help = new JButton("帮助");

		center = new JLabel(currentRoom.getDescription(), 0);
		nor = new JLabel("", 0);
		eas = new JLabel("", 0);
		sou = new JLabel("", 0);
		wes = new JLabel("", 0);

		reNewExit();

		left.add(north, BorderLayout.NORTH);
		left.add(east, BorderLayout.EAST);
		left.add(south, BorderLayout.SOUTH);
		left.add(west, BorderLayout.WEST);
		left.add(cent);

		cent.add(nor, BorderLayout.NORTH);
		cent.add(eas, BorderLayout.EAST);
		cent.add(center, BorderLayout.CENTER);
		cent.add(sou, BorderLayout.SOUTH);
		cent.add(wes, BorderLayout.WEST);

		cbg = new CheckboxGroup();
		right.add(new Checkbox("north", cbg, true));
		right.add(new Checkbox("east", cbg, false));
		right.add(new Checkbox("south", cbg, false));
		right.add(new Checkbox("west", cbg, false));

		right.add(text);
		right.add(build);
		right.add(help);

		panel.add(left, BorderLayout.CENTER);
		panel.add(right, BorderLayout.EAST);

	}

	private void reNewExit() {

		if (currentRoom.getNorthExit() != null) {
			nor.setText(currentRoom.getNorthExit().getDescription());
		} else {
			nor.setText("");
		}

		if (currentRoom.getEastExit() != null) {
			eas.setText(currentRoom.getEastExit().getDescription());
		} else {
			eas.setText("");
		}

		if (currentRoom.getSouthExit() != null) {
			sou.setText(currentRoom.getSouthExit().getDescription());
		} else {
			sou.setText("");
		}

		if (currentRoom.getWestExit() != null) {
			wes.setText(currentRoom.getWestExit().getDescription());
		} else {
			wes.setText("");
		}
		center.setText(currentRoom.getDescription());

	}

	private void start() {

		Scanner in = new Scanner(System.in);
		printWelcome();

		int time = 0;
		while (true) {

			String line = in.nextLine();
			String[] words = line.split(" ");

			if (words[0].equals("help")) {
				printHelp();
				time = 0;
			} else if (words[0].equals("go")) {
				goRoom(words[1]);
				time = 0;
			} else if (words[0].equals("bye")) {
				break;
			} else if (words[0].equals("build")) {
				buildRoom(currentRoom, words[1], words[2]);
				time = 0;
			} else {
				time++;
				switch (time) {
				case 1:
					System.out.println("啥？？");
					break;
				case 2:
					System.out.println("你说啥我不明白。。");
					break;
				case 3:
					System.out.println("请重新输入！");
					break;
				case 4:
					System.out.println("我再给你一次机会，把舌头捋直了再说- -");
					break;
				default:
					System.out.println("我怀疑你这里有问题！（指着脑袋）");

				}
			}

			frame.repaint();
		}

		System.out.println("感谢您的光临。再见！");
		in.close();
	}

	private void initMap() {

		map.put("城堡外", new Room("城堡外"));
		map.put("大堂", new Room("大堂"));
		map.put("小酒吧", new Room("小酒吧"));
		map.put("书房", new Room("书房"));
		map.put("卧室", new Room("卧室"));

		map.get("城堡外").setExits(null, map.get("大堂"), map.get("书房"), map.get("小酒吧"));
		map.get("大堂").setExits(null, null, null, map.get("城堡外"));
		map.get("小酒吧").setExits(null, map.get("城堡外"), null, null);
		map.get("书房").setExits(map.get("城堡外"), map.get("卧室"), null, null);
		map.get("卧室").setExits(null, null, null, map.get("书房"));

		currentRoom = map.get("城堡外"); // 从城堡门外开始
	}

	private void printWelcome() {
		System.out.println();
		System.out.println("欢迎参加城堡扩建计划！");
		System.out.println("这是一个没那么无聊的游戏。");
		System.out.println("如果需要帮助，请输入 'help' 。");
		System.out.println();

		showPrompt();
	}

	private void printHelp() {
		System.out.println("迷路了吗？你可以做的命令有：go bye help build");
		System.out.println("如：\tgo east");
		System.out.println("\tbuild newRoom west");
	}

	private void buildRoom(Room oldRoom, String newRoomName, String location) {
		if(map.containsKey(newRoomName)){
			JOptionPane.showMessageDialog(frame, newRoomName+"已经存在！");
			return;
		}
		map.put(newRoomName, new Room(newRoomName));

		if (location.equals("north")) {
			oldRoom.setNorthExit(map.get(newRoomName));
			map.get(newRoomName).setSouthExit(oldRoom);
		} else if (location.equals("south")) {
			oldRoom.setSouthExit(map.get(newRoomName));
			map.get(newRoomName).setNorthExit(oldRoom);
		} else if (location.equals("west")) {
			oldRoom.setWestExit(map.get(newRoomName));
			map.get(newRoomName).setEastExit(oldRoom);
		} else if (location.equals("east")) {
			oldRoom.setEastExit(map.get(newRoomName));
			map.get(newRoomName).setWestExit(oldRoom);
		}

		System.out.println(newRoomName + "建造完毕！");
		currentRoom = map.get(newRoomName);
		reNewExit();
		showPrompt();

	}

	private void goRoom(String direction) {
		Room nextRoom = null;
		if (direction.equals("north")) {
			nextRoom = currentRoom.getNorthExit();
		}
		if (direction.equals("east")) {
			nextRoom = currentRoom.getEastExit();
		}
		if (direction.equals("south")) {
			nextRoom = currentRoom.getSouthExit();
		}
		if (direction.equals("west")) {
			nextRoom = currentRoom.getWestExit();
		}

		if (nextRoom == null) {
			System.out.println("那里没有门！");
		} else {
			currentRoom = nextRoom;

			center.setText(currentRoom.getDescription());

			reNewExit();

			showPrompt();
		}
	}

	private void showPrompt() {
		System.out.println("你在" + currentRoom);
		System.out.print("出口有: ");
		if (currentRoom.getNorthExit() != null)
			System.out.print("north ");
		if (currentRoom.getEastExit() != null)
			System.out.print("east ");
		if (currentRoom.getSouthExit() != null)
			System.out.print("south ");
		if (currentRoom.getWestExit() != null)
			System.out.print("west ");
		System.out.println();
	}

	public static void main(String[] args) {
		Game game = new Game();

	}

}
