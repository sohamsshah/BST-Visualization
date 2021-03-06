import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class BSTVisualization extends JFrame implements ActionListener, KeyListener{
	// Tree Root Node.
	private Node root;

	private Node tempColor;
	private Color color;
	private JButton btnAdd, btnDelete;
	private JTextField tf;
	private int X = 300, Y = 75;
	private Graphics2D g2;
	private Rectangle size;
	private JLabel labelInorder,labelPreorder,labelPostorder,labelHeight;
	private JLabel ansInorder,ansPreorder,ansPostorder,ansHeight;

	//Node Structure
	private static class Node {
		JLabel data;
		Node left;
		Node right;
		Points p;

		Node(int info) {
			data = new JLabel(info + "", SwingConstants.CENTER);
			data.setFont(new Font("Arial", Font.BOLD, 20));
			data.setBorder(BorderFactory.createLineBorder(Color.black));
			data.setOpaque(true);
			data.setBackground(Color.green);
			p = null;
		}

		void setPoints(int x1, int y1, int x2, int y2) {
			p = new Points(x1, y1, x2, y2);
		}
	}

	//Points structure
	private static class Points {
		int x1 = 0, x2 = 0, y2 = 0, y1 = 0;

		Points(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.x2 = x2;
			this.y2 = y2;
			this.y1 = y1;
		}

		public String toString() {
			return "x1 = " + x1 + ", y1 = " + y1 + ", x2 = " + x2 + ", y2 = " + y2;
		}
	}

	public void paint(Graphics g) {
		super.paintComponents(g);

		g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3.0f));

		Stack<Node> s = new Stack<>();
		Node curr = root;
		Points pts;
		while (!s.isEmpty() || curr != null) {
			while (curr != null) {
				s.push(curr);
				curr = curr.left;
			}
			if (!s.isEmpty())
				curr = s.pop();
			pts = curr.p;
			g2.drawLine(pts.x1 + 7, pts.y1 + 30, pts.x2 + 3, pts.y2 + 10);
			curr = curr.right;
		}

		// x1 = label.getX()+7
		// y1 = label.getY()+30
	}

	public BSTVisualization() {
		setLayout(null); // layout
		setSize(1200, 700); //frame size

		size = getBounds();
		X = size.width / 2;

		// Height of BST label
		labelHeight = new JLabel("BST Height : ");
		labelHeight.setFont(new Font("Calibri", Font.BOLD, 20));
		labelHeight.setBounds(30, 20, 150, 30);
		add(labelHeight);

		// Height of BST answer
		ansHeight = new JLabel("0");
		ansHeight.setFont(new Font("Calibri", Font.BOLD, 20));
		ansHeight.setBounds(135, 20, 50, 30);
		add(ansHeight);

		//For geting data.
		tf = new JTextField("");
		tf.setFont(new Font("Arial", Font.BOLD, 20));
		tf.setBounds(size.width - 300, 20, 150, 30);
		tf.addKeyListener(this);
		add(tf);

		//Add Button
		btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Arial", Font.BOLD, 20));
		btnAdd.setBounds(size.width - 130, 20, 100, 30);
		btnAdd.addActionListener(this);
		add(btnAdd);

		//Delete Button
		btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Arial", Font.BOLD, 20));
		btnDelete.setBounds(size.width - 130, 60, 100, 30);
		btnDelete.addActionListener(this);
		add(btnDelete);


		// Inorder label
		labelInorder = new JLabel("Inorder :");
		labelInorder.setFont(new Font("Times New Roman", Font.BOLD, 20));
		labelInorder.setBounds(30, 450, 100, 30);
		add(labelInorder);

		// Inorder traversal answer
		ansInorder = new JLabel("BST is empty.");
		ansInorder.setFont(new Font("Arial", Font.PLAIN, 18));
		/* For Border and Background. */
		// ansInorder.setOpaque(true);
		// ansInorder.setBackground(Color.white);
		// ansInorder.setBorder(new LineBorder(Color.black));
		ansInorder.setBounds(30, 480, 1130, 24);
		add(ansInorder);

		// Preorder label
		labelPreorder = new JLabel("Preorder :");
		labelPreorder.setFont(new Font("Times New Roman", Font.BOLD, 20));
		labelPreorder.setBounds(30, 520, 100, 30);
		add(labelPreorder);

		// Preorder traversal answer
		ansPreorder = new JLabel("BST is empty.");
		ansPreorder.setFont(new Font("Arial", Font.PLAIN, 18));
		// ansPreorder.setOpaque(true);
		// ansPreorder.setBackground(Color.white);
		// ansPreorder.setBorder(new LineBorder(Color.black));
		ansPreorder.setBounds(30, 550, 1130, 24);
		add(ansPreorder);

		// Postorder label
		labelPostorder = new JLabel("Postorder :");
		labelPostorder.setFont(new Font("Times New Roman", Font.BOLD, 20));
		labelPostorder.setBounds(30, 590, 100, 30);
		add(labelPostorder);

		// Postorder traversal answer
		ansPostorder = new JLabel("BST is empty.");
		ansPostorder.setFont(new Font("Arial", Font.PLAIN, 18));
		// ansPostorder.setOpaque(true);
		// ansPostorder.setBackground(Color.white);
		// ansPostorder.setBorder(new LineBorder(Color.black));
		ansPostorder.setBounds(30, 620, 1130, 24);
		add(ansPostorder);

		tf.requestFocusInWindow();

		setTitle("Binary Search Tree Visualization"); //Title Frame
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(tf.isEnabled()){
			try {
				int data = Integer.parseInt(tf.getText());
				if (evt.getSource() == btnAdd) {
					add(data);
				} else {
					delete(data);
				}
				tf.setText("");
				tf.requestFocusInWindow();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Please Enter Integer.");
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		char c = evt.getKeyChar();
		if(!tf.isEnabled()){
			return;
		}
		else if (c == 'a' || c == 'A' || c == '\n' || c == '+') {
			try {
				String data = tf.getText();
				evt.consume(); // Not type 'a' or 'A' character in textfield
				if (!data.isEmpty()) {
					add(Integer.parseInt(data));
				}else{
					throw new Exception();
				}
				tf.requestFocusInWindow();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Please Enter Integer.");
			}
			tf.setText("");
		} else if (c == 'd' || c == 'D' || c == '-') {
			try {
				String data = tf.getText();
				evt.consume(); // Not type 'd' or 'D' character in textfield
				if (!data.isEmpty()) {
					delete(Integer.parseInt(data));
				}
				tf.requestFocusInWindow();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Please Enter Integer.");
			}
			tf.setText("");
		} else if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')
			evt.consume();
	}

	@Override
	public void keyPressed(KeyEvent evt) {
	}

	@Override
	public void keyReleased(KeyEvent evt) {
	}

	//Add element in BST.
	public void add(int info) {
		Node newNode = new Node(info);

		if (root == null) {
			root = newNode;
			newNode.data.setBounds(600, 10, 40, 40);
			newNode.p = new Points(0, 0, 0, 0);
		} else {
			Node curr = root, pre = root;
			int temp;
			X = getBounds().width / 2;
			while (curr != null) {
				pre = curr;
				temp = Integer.parseInt(curr.data.getText());
				if (info == temp) {
					JOptionPane.showMessageDialog(null, info + " is already exist.");
					return;
				} else if (temp > info) {
					curr = curr.left;
				} else {
					curr = curr.right;
				}
				X /= 2;
			}
			temp = Integer.parseInt(pre.data.getText());
			int x = pre.data.getX();
			int y = pre.data.getY();
			if (temp > info) {
				pre.left = newNode;
				newNode.data.setBounds(x - X, y + Y, 40, 40);
				// x1=x;y1=y+20;x2=x-X+20;y2=y+Y+20;
				newNode.p = new Points(x, y + 20, x - X + 20, y + Y + 20);
			} else {
				pre.right = newNode;
				newNode.data.setBounds(x + X, y + Y, 40, 40);
				// x1=x+40;y1=y+20;x2=x+X+20;y2=y+Y+20;
				newNode.p = new Points(x + 40, y + 20, x + X + 20, y + Y + 20);
			}
		}
		
		// Set all traversal and height of BST
		setInfo();

		paint(getGraphics());
		add(newNode.data);
		validate();
		repaint();
	}

	// Delete Node from BST
	public void delete(int data) {
		if (root == null) {
			JOptionPane.showMessageDialog(null, "BST is empty.");
		} else {
			Node curr = root, pre = root;

			while (curr != null) {
				int info = Integer.parseInt(curr.data.getText());
				if (info == data) {
					break;
				} else if (info > data) {
					pre = curr;
					curr = curr.left;
				} else {
					pre = curr;
					curr = curr.right;
				}
			}

			if (curr == null) { // data is not find.
				JOptionPane.showMessageDialog(null, data + " is not available.");
				return;
			} else if (curr.left == null || curr.right == null) { // data has 0 or 1 child

				this.remove(curr.data);
				revalidate();
				repaint();

				if (curr != root) {
					Node address = curr.left != null ? curr.left : curr.right;
					// curr.data>pre.data
					int preData = Integer.parseInt(pre.data.getText());
					int currData = Integer.parseInt(curr.data.getText());
					if (currData > preData) {
						pre.right = address;
					} else {
						pre.left = address;
					}
				} else {
					if (curr.left != null) {
						root = curr.left;
					} else {
						root = curr.right;
					}
				}

				reArrangeNode(root, root, getBounds().width / 2);

			} else { // data has 2 child.

				this.remove(curr.data);
				revalidate();
				repaint();

				/*
				 It set another node depending upon the height of left and right sub tree.
				 */
				Node nextRoot = null, preRoot = curr;
				int leftSubHeight = calculateHeight(curr.left);
				int rightSubHeight = calculateHeight(curr.right);

				/* For taking maximum element from the left Side. */
				if (leftSubHeight > rightSubHeight) {
					nextRoot = curr.left;
					while (nextRoot.right != null) {
						preRoot = nextRoot;
						nextRoot = nextRoot.right;
					}

					if (preRoot != curr) {
						preRoot.right = nextRoot.left;
					} else {
						preRoot.left = nextRoot.left;
					}
				} else { /* For taking minimum element from the right Side.*/
					nextRoot = curr.right;
					while (nextRoot.left != null) {
						preRoot = nextRoot;
						nextRoot = nextRoot.left;
					}

					if (preRoot != curr) {
						preRoot.left = nextRoot.right;
					} else {
						preRoot.right = nextRoot.right;
					}
				}

				curr.data = nextRoot.data;
				reArrangeNode(root, root, getBounds().width / 2);
			}
		}
		
		// Set all traversal and height of BST
		setInfo();
	}

	// Set all traversal and height of BST
	private void setInfo(){
		int height = calculateHeight(root);

		if(height == 0){
			ansInorder.setText("BST is empty.");
			ansPostorder.setText("BST is empty.");
			ansPreorder.setText("BST is empty.");
		} else {
			ansInorder.setText(inorder(root));
			ansPostorder.setText(postorder(root));
			ansPreorder.setText(preorder(root));
		}

		ansHeight.setText(height+"");
	}

	//Inorder logic
	private String inorder(Node root) {
		if (root == null)
			return "";

		return inorder(root.left) + root.data.getText() + " " + inorder(root.right);
	}

	//Preorder logic
	public String preorder(Node root) {
		if (root == null)
			return "";

		return root.data.getText() + " " + preorder(root.left) + preorder(root.right);
	}

	//Postorder logic
	public String postorder(Node root) {
		if (root == null)
			return "";

		return postorder(root.left) + postorder(root.right) + root.data.getText() + " ";
	}

	// Calculate Height of BST using recursive method.
	private int calculateHeight(Node root) {
		if (root == null) {
			return 0;
		}

		return 1 + Math.max(calculateHeight(root.left), calculateHeight(root.right));
	}

	// Rearrange nodes
	private void reArrangeNode(Node node, Node pre, int X) {
		if (node == null)
			return;

		if (root == node) {
			node.data.setBounds(X, 10, 40, 40);
			node.p = new Points(0, 0, 0, 0);
		} else {
			int x = pre.data.getX();
			int y = pre.data.getY();
			int preData = Integer.parseInt(pre.data.getText());
			int nodeData = Integer.parseInt(node.data.getText());
			if (nodeData < preData) {
				node.data.setBounds(x - X, y + Y, 40, 40);
				node.p = new Points(x, y + 20, x - X + 20, y + Y + 20);
			} else {
				node.data.setBounds(x + X, y + Y, 40, 40);
				node.p = new Points(x + 40, y + 20, x + X + 20, y + Y + 20);
			}
		}

		reArrangeNode(node.left, node, X / 2);
		reArrangeNode(node.right, node, X / 2);
	}

	public static void main(String arg[]) {
		BSTVisualization bst = new BSTVisualization();

		// bst.add(50);
		// bst.add(25);
		// bst.add(35);
		// bst.add(20);
		// bst.add(75);
		// bst.add(100);
		// bst.add(70);
		// bst.add(74);
	}
}