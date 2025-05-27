import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class Projectjava extends JFrame implements ActionListener 
{
    private ImageIcon img1, img2, img3;
    private JLabel mangojuiceLabel, lacchiLabel, lemonadeLabel,imgLabel1, imgLabel2, imgLabel3,mangojuicePriceLabel, lacchiPriceLabel, lemonadePriceLabel;
    private JTextField mangojuiceTF, lacchiTF, lemonadeTF;
    private JTextArea purchaseHistoryArea;
    private JButton clearBtn, cartBtn, purchaseBtn, deleteBtn;
    private JScrollPane scrollPane;
    private JPanel panel;
    private String lastOrderEntry = "";

    public Projectjava()
	{
        super("Juice Bar Management System");
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeUI();
    }

    private void initializeUI()
	{
        panel = new JPanel();
        panel.setLayout(null);

        img1 = new ImageIcon("Images/mangojuice.jpg");
        img2 = new ImageIcon("Images/lacchi.jpg");
        img3 = new ImageIcon("Images/lemonade.jpg");

        imgLabel1 = new JLabel(new ImageIcon(img1.getImage().getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH)));
        imgLabel1.setBounds(80, 20, 80, 80);
        panel.add(imgLabel1);

        mangojuicePriceLabel = new JLabel("Price: 100 Taka");
        mangojuicePriceLabel.setBounds(80, 100, 160, 30);
        panel.add(mangojuicePriceLabel);

        mangojuiceLabel = new JLabel("Mango Juice Quantity:");
        mangojuiceLabel.setBounds(200, 50, 150, 30);
        panel.add(mangojuiceLabel);

        mangojuiceTF = new JTextField();
        mangojuiceTF.setBounds(200, 80, 100, 30);
        panel.add(mangojuiceTF);

        imgLabel2 = new JLabel(new ImageIcon(img2.getImage().getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH)));
        imgLabel2.setBounds(80, 150, 80, 80);
        panel.add(imgLabel2);

        lacchiPriceLabel = new JLabel("Price: 80 Taka");
        lacchiPriceLabel.setBounds(80, 230, 160, 30);
        panel.add(lacchiPriceLabel);

        lacchiLabel = new JLabel("Lacchi Quantity:");
        lacchiLabel.setBounds(200, 180, 150, 30);
        panel.add(lacchiLabel);

        lacchiTF = new JTextField();
        lacchiTF.setBounds(200, 210, 100, 30);
        panel.add(lacchiTF);

        imgLabel3 = new JLabel(new ImageIcon(img3.getImage().getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH)));
        imgLabel3.setBounds(80, 280, 80, 80);
        panel.add(imgLabel3);

        lemonadePriceLabel = new JLabel("Price: 50 Taka");
        lemonadePriceLabel.setBounds(80, 360, 160, 30);
        panel.add(lemonadePriceLabel);

        lemonadeLabel = new JLabel("Lemonade Quantity:");
        lemonadeLabel.setBounds(200, 310, 150, 30);
        panel.add(lemonadeLabel);

        lemonadeTF = new JTextField();
        lemonadeTF.setBounds(200, 340, 100, 30);
        panel.add(lemonadeTF);

        clearBtn = new JButton("Clear");
        clearBtn.setBounds(350, 150, 150, 30);
        clearBtn.addActionListener(this);
        panel.add(clearBtn);

        cartBtn = new JButton("Add To Cart");
        cartBtn.setBounds(350, 190, 150, 30);
        cartBtn.addActionListener(this);
        panel.add(cartBtn);

        purchaseBtn = new JButton("Confirm Purchase");
        purchaseBtn.setBounds(350, 230, 150, 30);
        purchaseBtn.addActionListener(this);
        panel.add(purchaseBtn);

        deleteBtn = new JButton("Delete From Cart");
        deleteBtn.setBounds(350, 270, 150, 30);
        deleteBtn.addActionListener(this);
        panel.add(deleteBtn);

        purchaseHistoryArea = new JTextArea();
        purchaseHistoryArea.setEditable(false);
        scrollPane = new JScrollPane(purchaseHistoryArea);
        scrollPane.setBounds(550, 20, 400, 520);
        panel.add(scrollPane);

        this.add(panel);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
	{
        if (e.getSource() == cartBtn)
			{
            addToCart();
			} 
		else if (e.getSource() == purchaseBtn)
			{
            showPurchaseHistory();
			} 
		else if (e.getSource() == clearBtn)
			{
            clearFields();
			}
		else if (e.getSource() == deleteBtn)
			{
            deleteFromCart();
			}
    }

    private void addToCart()
	{
        try {
            int mango = getQuantity(mangojuiceTF);
            int lacchi = getQuantity(lacchiTF);
            int lemon = getQuantity(lemonadeTF);

            if (mango == 0 && lacchi == 0 && lemon == 0) 
			{
                JOptionPane.showMessageDialog(this, "Please enter at least one item.");
                return;
            }

            lastOrderEntry = String.format(
                "Mango: %d, Lacchi: %d, Lemonade: %d, Total: %d Taka%n",
                mango, lacchi, lemon, mango * 100 + lacchi * 80 + lemon * 50
            );
            saveOrder(lastOrderEntry);
            JOptionPane.showMessageDialog(this, "Added to cart!");
            clearFields();
        } 
		catch (NumberFormatException ex)
		{
            JOptionPane.showMessageDialog(this, "Invalid input! Use numbers only.");
        }
		catch (IOException ex) 
		{
            JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage());
        }
    }

    private int getQuantity(JTextField field) throws NumberFormatException 
	{
        String text = field.getText().trim();
        if (text.isEmpty()) return 0;
        int quantity = Integer.parseInt(text);
        if (quantity < 0) throw new NumberFormatException("Negative quantity");
        return quantity;
    }

    private void saveOrder(String entry) throws IOException 
	{
        FileWriter fw = new FileWriter("PurchaseData.txt", true);
        fw.write(entry);
        fw.close();
    }

    private void showPurchaseHistory()
	{
        File file = new File("PurchaseData.txt");
        if (!file.exists()) 
		{
            JOptionPane.showMessageDialog(this, "No purchase history found!");
            return;
        }
        try  {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line, history = "";
            while ((line = br.readLine()) != null) 
			{
                history += line + "\n";
            }
            br.close();
            purchaseHistoryArea.setText(history);
             }
			  catch (IOException ex) 
			 {
            JOptionPane.showMessageDialog(this, "Error reading history: " + ex.getMessage());
        }
    }

    private void deleteFromCart() 
	{
        File file = new File("PurchaseData.txt");
        if (!file.exists() || lastOrderEntry.isEmpty())
		{
            JOptionPane.showMessageDialog(this, "Nothing to delete.");
            return;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line;
            boolean deleted = false;

            while ((line = br.readLine()) != null) 
			{
                String entry = line + "\n";
                if (!deleted && entry.equals(lastOrderEntry)) 
				{
                    deleted = true;
                    continue;
                }
                content.append(entry);
            }
            br.close();

            FileWriter fw = new FileWriter(file);
            fw.write(content.toString());
            fw.close();

            purchaseHistoryArea.setText(content.toString());
            lastOrderEntry = "";
            JOptionPane.showMessageDialog(this, "Last cart entry deleted.");
        } 
		catch (IOException ex) 
		{
            JOptionPane.showMessageDialog(this, "Error deleting entry: " + ex.getMessage());
        }
    }

    private void clearFields()
	{
        mangojuiceTF.setText("");
        lacchiTF.setText("");
        lemonadeTF.setText("");
    }

    public static void main(String[] args) 
	{
        SwingUtilities.invokeLater(() -> new Projectjava().setVisible(true));
    }
}
