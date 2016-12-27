package castle;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Game {
	private Room currentRoom;
	private HashMap<String, Room> roomMap = new HashMap<String, Room>();
	private ArrayList<String> imageList = new ArrayList<String>();

	private JButton goNorth, goEast, goSouth, goWest, build, help;
	private JPanel left, right, map, end;
	private JLabel northRoom, eastRoom, southRoom, westRoom, centerRoom;

	private JFrame frame;
	private JPanel panel;
	private CheckboxGroup cbg;
	private JTextField text;
	private JLabel print, human, change;

	public Game() {
		initData();
		initView();
		addListener();
	}

	private void initData() {

		roomMap.put("城堡外", new Room("城堡外"));
		roomMap.put("大堂", new Room("大堂"));
		roomMap.put("小酒吧", new Room("小酒吧"));
		roomMap.put("书房", new Room("书房"));
		roomMap.put("卧室", new Room("卧室"));

		roomMap.get("城堡外").setExits(null, roomMap.get("大堂"), roomMap.get("书房"), roomMap.get("小酒吧"));
		roomMap.get("大堂").setExits(null, null, null, roomMap.get("城堡外"));
		roomMap.get("小酒吧").setExits(null, roomMap.get("城堡外"), null, null);
		roomMap.get("书房").setExits(roomMap.get("城堡外"), roomMap.get("卧室"), null, null);
		roomMap.get("卧室").setExits(null, null, null, roomMap.get("书房"));

		currentRoom = roomMap.get("城堡外"); // 从城堡门外开始

	}

	private void initView() {
		frame = new JFrame();

		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		fillPanel();

		Toolkit kit;
		Dimension screenSize;

		kit = Toolkit.getDefaultToolkit();
		screenSize = kit.getScreenSize();

		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		frame.setSize(screenWidth / 2, screenHeight / 2);
		frame.setLocationByPlatform(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("城堡扩建计划");
		frame.add(panel);

		reNewExit();
		drawRoomIcon();

		frame.setVisible(true);

	}

	private void drawRoomIcon() {

		imageList.add("h1.png");
		imageList.add("h2.png");
		imageList.add("h3.png");
		imageList.add("h4.png");
		imageList.add("h5.png");
		imageList.add("h6.png");

		roomMap.get("城堡外").setIcon(imageList.get(0));
		roomMap.get("书房").setIcon(imageList.get(1));
		roomMap.get("大堂").setIcon(imageList.get(2));
		roomMap.get("小酒吧").setIcon(imageList.get(3));
		roomMap.get("卧室").setIcon(imageList.get(4));
		reNewIcon();
	}

	private void addListener() {
		goNorth.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goRoom("north");
			}
		});
		goSouth.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goRoom("south");
			}
		});
		goWest.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goRoom("west");
			}
		});
		goEast.addActionListener(new ActionListener() {

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
				JOptionPane.showMessageDialog(frame,
						"迷路了吗？你可以点击地图周围的按钮来移动自己的位置\n\n你还可以在当前位置选择一个没有建筑的方向建造新的建筑物。\n\n\t具体操作是：\n\t在四个方向中选择一个合适的方向，并在输入框中为新建筑命名\n\t，然后点击建造。",
						"确定", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	private void fillPanel() {
		left = new JPanel();
		right = new JPanel();
		end = new JPanel();
		map = new JPanel();
		text = new JTextField();
		human = new JLabel("嗯，这个游戏叫做——城堡扩建计划！");
		change = new JLabel("它的确挺无聊的，哈哈~");
		print = new JLabel("如果需要帮助，请点击 '帮助' 。");
		goNorth = new JButton("北(N)");
		goEast = new JButton("东(E)");
		goSouth = new JButton("南(S)");
		goWest = new JButton("西(W)");
		build = new JButton("建造");
		help = new JButton("帮助");
		centerRoom = new JLabel(currentRoom.getDescription(), 0);
		northRoom = new JLabel("", 0);
		eastRoom = new JLabel("", 0);
		southRoom = new JLabel("", 0);
		westRoom = new JLabel("", 0);
		cbg = new CheckboxGroup();

		left.setLayout(new BorderLayout());
		right.setLayout(new GridLayout(7, 1));
		end.setLayout(new GridLayout(3, 1));
		map.setLayout(new BorderLayout());

		centerRoom.setFont(new Font("TimesRoman", Font.BOLD, 20));
		northRoom.setFont(new Font("TimesRoman", Font.BOLD, 20));
		eastRoom.setFont(new Font("TimesRoman", Font.BOLD, 20));
		southRoom.setFont(new Font("TimesRoman", Font.BOLD, 20));
		westRoom.setFont(new Font("TimesRoman", Font.BOLD, 20));

		left.add(goNorth, BorderLayout.NORTH);
		left.add(goEast, BorderLayout.EAST);
		left.add(goSouth, BorderLayout.SOUTH);
		left.add(goWest, BorderLayout.WEST);
		left.add(map);

		end.add(human);
		end.add(change);
		end.add(print);

		map.add(northRoom, BorderLayout.NORTH);
		map.add(eastRoom, BorderLayout.EAST);
		map.add(southRoom, BorderLayout.SOUTH);
		map.add(westRoom, BorderLayout.WEST);
		map.add(centerRoom, BorderLayout.CENTER);

		right.add(new Checkbox("north", cbg, true));
		right.add(new Checkbox("east", cbg, false));
		right.add(new Checkbox("south", cbg, false));
		right.add(new Checkbox("west", cbg, false));

		right.add(text);
		right.add(build);
		right.add(help);

		panel.add(left, BorderLayout.CENTER);
		panel.add(right, BorderLayout.EAST);
		panel.add(end, BorderLayout.SOUTH);

	}

	private void reNewIcon() {
		centerRoom.setIcon(new ImageIcon(currentRoom.getIcon()));

	}

	private void reNewExit() {

		if (currentRoom.getNorthExit() != null) {
			northRoom.setText(currentRoom.getNorthExit().getDescription());
		} else {
			northRoom.setText("");
		}

		if (currentRoom.getEastExit() != null) {
			eastRoom.setText(currentRoom.getEastExit().getDescription());
		} else {
			eastRoom.setText("");
		}

		if (currentRoom.getSouthExit() != null) {
			southRoom.setText(currentRoom.getSouthExit().getDescription());
		} else {
			southRoom.setText("");
		}

		if (currentRoom.getWestExit() != null) {
			westRoom.setText(currentRoom.getWestExit().getDescription());
		} else {
			westRoom.setText("");
		}
		centerRoom.setText(currentRoom.getDescription());

	}

	private void buildRoom(Room oldRoom, String newRoomName, String location) {
		if (oldRoom.getExit(location) != null) {
			JOptionPane.showMessageDialog(frame, "那里已经有建筑了！");
			return;
		}
		if (newRoomName.equals("")) {
			JOptionPane.showMessageDialog(frame, "总得给那地方取个名字吧！");
			return;
		}
		if (roomMap.containsKey(newRoomName)) {
			JOptionPane.showMessageDialog(frame, newRoomName + "已经存在！");
			return;
		}
		roomMap.put(newRoomName, new Room(newRoomName));

		if (location.equals("north")) {
			oldRoom.setNorthExit(roomMap.get(newRoomName));
			roomMap.get(newRoomName).setSouthExit(oldRoom);
		} else if (location.equals("south")) {
			oldRoom.setSouthExit(roomMap.get(newRoomName));
			roomMap.get(newRoomName).setNorthExit(oldRoom);
		} else if (location.equals("west")) {
			oldRoom.setWestExit(roomMap.get(newRoomName));
			roomMap.get(newRoomName).setEastExit(oldRoom);
		} else if (location.equals("east")) {
			oldRoom.setEastExit(roomMap.get(newRoomName));
			roomMap.get(newRoomName).setWestExit(oldRoom);
		}

		print.setText(newRoomName + "建造完毕！");
		currentRoom = roomMap.get(newRoomName);
		currentRoom.setIcon(imageList.get(new Random().nextInt(6)));
		reNewExit();
		reNewIcon();

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
			JOptionPane.showMessageDialog(frame, "那里没有门！");
			// System.out.println("那里没有门！");
		} else {
			currentRoom = nextRoom;

			reNewIcon();
			centerRoom.setText(currentRoom.getDescription());

			reNewExit();

		}
	}

	public static void main(String[] args) {
		Game game = new Game();

	}

}
