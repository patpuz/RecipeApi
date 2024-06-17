package org.example.hibernate;

import org.example.hibernate.model.User;
import org.example.hibernate.model.Ingredient;
import org.example.hibernate.model.Recipe;
import org.example.hibernate.service.IngredientService;
import org.example.hibernate.service.RecipeService;
import org.example.hibernate.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class GUI {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private UserService userService;
    private IngredientService ingredientService;
    private RecipeService recipeService;
    private User loggedInUser;

    public GUI() {
        userService = new UserService();
        ingredientService = new IngredientService();
        recipeService = new RecipeService();
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 380, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        Color backgroundColor = new Color(173, 216, 230);
        Color buttonColor = new Color(135, 206, 235);
        Color textColor = Color.WHITE;

        frame.getContentPane().setBackground(backgroundColor);
        frame.getContentPane().setForeground(textColor);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(60, 50, 90, 15);
        lblUsername.setForeground(textColor);
        frame.getContentPane().add(lblUsername);

        usernameField = new JTextField();
        usernameField.setBounds(150, 46, 130, 25);
        frame.getContentPane().add(usernameField);
        usernameField.setColumns(10);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(60, 100, 95, 16);
        lblPassword.setForeground(textColor);
        frame.getContentPane().add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 96, 130, 25);
        frame.getContentPane().add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(110, 160, 115, 30);
        styleButton(loginButton, buttonColor, textColor);
        frame.getContentPane().add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setBounds(110, 200, 115, 30);
        styleButton(registerButton, buttonColor, textColor);
        frame.getContentPane().add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRegistrationScreen();
            }
        });

        frame.setVisible(true);
    }

    private void styleButton(JButton button, Color backgroundColor, Color textColor) {
        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }



    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        List<User> users = userService.findAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                showMainScreen();
                return;
            }
        }
        showMessageDialog("Login Failed", "Invalid username or password.", JOptionPane.ERROR_MESSAGE);
    }

    private void showMessageDialog(String title, String message, int messageType) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);
        panel.add(label);
        JOptionPane.showMessageDialog(frame, panel, title, messageType);
    }

    private void showMainScreen() {
        frame.getContentPane().removeAll();

        Color buttonColor = new Color(135, 206, 235);
        Color textColor = Color.WHITE;

        JButton addIngredientButton = new JButton("Add Ingredient");
        addIngredientButton.setBounds(80, 40, 200, 30);
        styleButton(addIngredientButton, buttonColor, textColor);
        frame.getContentPane().add(addIngredientButton);

        JButton showIngredientsButton = new JButton("Show Ingredients");
        showIngredientsButton.setBounds(80, 90, 200, 30);
        styleButton(showIngredientsButton, buttonColor, textColor);
        frame.getContentPane().add(showIngredientsButton);

        JButton findRecipesButton = new JButton("Find Recipes");
        findRecipesButton.setBounds(80, 140, 200, 30);
        styleButton(findRecipesButton, buttonColor, textColor);
        frame.getContentPane().add(findRecipesButton);

        JButton showSavedRecipesButton = new JButton("Show Saved Recipes");
        showSavedRecipesButton.setBounds(80, 190, 200, 30);
        styleButton(showSavedRecipesButton, buttonColor, textColor);
        frame.getContentPane().add(showSavedRecipesButton);

        addIngredientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleAddIngredient();
            }
        });

        showIngredientsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleShowIngredients();
            }
        });

        findRecipesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleFindRecipes();
            }
        });

        showSavedRecipesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleShowSavedRecipes();
            }
        });

        frame.revalidate();
        frame.repaint();
    }

    private void showRegistrationScreen() {
        frame.getContentPane().removeAll();

        Color buttonColor = new Color(135, 206, 235);
        Color textColor = Color.WHITE;

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(60, 30, 90, 15);
        lblUsername.setForeground(textColor);
        frame.getContentPane().add(lblUsername);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 26, 130, 25);
        frame.getContentPane().add(usernameField);
        usernameField.setColumns(10);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(60, 70, 95, 16);
        lblPassword.setForeground(textColor);
        frame.getContentPane().add(lblPassword);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 66, 130, 25);
        frame.getContentPane().add(passwordField);

        JLabel lblConfirmPassword = new JLabel("Confirm Password");
        lblConfirmPassword.setBounds(30, 110, 120, 16);
        lblConfirmPassword.setForeground(textColor);
        frame.getContentPane().add(lblConfirmPassword);

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(150, 106, 130, 25);
        frame.getContentPane().add(confirmPasswordField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(110, 160, 115, 30);
        styleButton(registerButton, buttonColor, textColor);
        frame.getContentPane().add(registerButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(110, 200, 115, 30);
        styleButton(backButton, buttonColor, textColor);
        frame.getContentPane().add(backButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (!password.equals(confirmPassword)) {
                    showMessageDialog("Registration Failed", "Passwords do not match.", JOptionPane.ERROR_MESSAGE);
                } else {
                    userService.createUser(username, password);
                    showMessageDialog("Registration Successful", "User registered successfully!", JOptionPane.INFORMATION_MESSAGE);
                    showLoginScreen();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showLoginScreen();
            }
        });

        frame.revalidate();
        frame.repaint();
    }

    private void showLoginScreen() {
        frame.getContentPane().removeAll();
        initialize();
    }

    private void handleAddIngredient() {
        String ingredientName = JOptionPane.showInputDialog(frame, "Enter ingredient name:");
        if (ingredientName != null && !ingredientName.isEmpty()) {
            ingredientService.createIngredient(loggedInUser.getId(), ingredientName);
            showMessageDialog("Success", "Ingredient added successfully!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleRemoveIngredient(Long ingredientId) {
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this ingredient?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            ingredientService.deleteIngredient(ingredientId);
            showMessageDialog("Success", "Ingredient deleted successfully!", JOptionPane.INFORMATION_MESSAGE);
            handleShowIngredients();
        }
    }

    private void handleShowIngredients() {
        List<Ingredient> ingredients = ingredientService.findIngredientsByUserId(loggedInUser.getId());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        Color backgroundColor = new Color(173, 216, 230);
        Color textColor = Color.BLACK;
        panel.setBackground(backgroundColor);

        if (!ingredients.isEmpty()) {
            for (Ingredient ingredient : ingredients) {
                JPanel ingredientPanel = new JPanel();
                ingredientPanel.setLayout(new BorderLayout());
                ingredientPanel.setBackground(backgroundColor);

                JLabel nameLabel = new JLabel(ingredient.getIngredientName());
                nameLabel.setForeground(textColor);
                ingredientPanel.add(nameLabel, BorderLayout.CENTER);

                JButton deleteButton = new JButton("X");
                styleButton(deleteButton, new Color(255, 69, 0), Color.WHITE);
                deleteButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handleRemoveIngredient(ingredient.getId());
                    }
                });
                ingredientPanel.add(deleteButton, BorderLayout.EAST);

                panel.add(ingredientPanel);
            }
        } else {
            JLabel noIngredientsLabel = new JLabel("No ingredients found.");
            noIngredientsLabel.setForeground(textColor);
            panel.add(noIngredientsLabel);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        styleScrollPane(scrollPane, backgroundColor);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(frame, scrollPane, "Ingredients", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleFindRecipes() {
        List<Ingredient> ingredients = ingredientService.findIngredientsByUserId(loggedInUser.getId());
        List<String> ingredientNames = ingredients.stream().map(Ingredient::getIngredientName).collect(Collectors.toList());
        List<Recipe> recipes;
        try {
            recipes = recipeService.findRecipesByIngredients(ingredientNames);
        } catch (IOException e) {
            showMessageDialog("Error", "Error finding recipes: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        Color backgroundColor = new Color(173, 216, 230);
        Color textColor = Color.BLACK;
        panel.setBackground(backgroundColor);

        for (Recipe recipe : recipes) {
            JPanel recipePanel = new JPanel();
            recipePanel.setLayout(new BorderLayout());
            recipePanel.setBackground(backgroundColor);

            JLabel titleLabel = new JLabel(recipe.getTitle());
            titleLabel.setForeground(textColor);
            recipePanel.add(titleLabel, BorderLayout.NORTH);

            if (recipe.getImage() != null && !recipe.getImage().isEmpty()) {
                ImageIcon imageIcon = getImageFromUrl(recipe.getImage());
                if (imageIcon != null) {
                    JLabel imageLabel = new JLabel(imageIcon);
                    recipePanel.add(imageLabel, BorderLayout.CENTER);
                }
            }

            JButton saveButton = new JButton("Save");
            styleButton(saveButton, new Color(60, 179, 113), Color.WHITE);
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    recipeService.createRecipe(loggedInUser.getId(), recipe.getTitle(), recipe.getImage());
                    showMessageDialog("Success", "Recipe saved successfully!", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            recipePanel.add(saveButton, BorderLayout.SOUTH);

            panel.add(recipePanel);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        styleScrollPane(scrollPane, backgroundColor);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(frame, scrollPane, "Recipes", JOptionPane.INFORMATION_MESSAGE);
    }

    private void styleScrollPane(JScrollPane scrollPane, Color backgroundColor) {
        scrollPane.getViewport().setBackground(backgroundColor);
    }

    private ImageIcon getImageFromUrl(String url) {
        try {
            return new ImageIcon(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void handleShowSavedRecipes() {
        List<Recipe> recipes = recipeService.findRecipesByUserId(loggedInUser.getId());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        Color backgroundColor = new Color(173, 216, 230);
        Color textColor = Color.BLACK;
        panel.setBackground(backgroundColor);

        for (Recipe recipe : recipes) {
            JPanel recipePanel = new JPanel();
            recipePanel.setLayout(new BorderLayout());
            recipePanel.setBackground(backgroundColor);

            JLabel titleLabel = new JLabel(recipe.getTitle());
            titleLabel.setForeground(textColor);
            recipePanel.add(titleLabel, BorderLayout.NORTH);

            if (recipe.getImage() != null && !recipe.getImage().isEmpty()) {
                ImageIcon imageIcon = getImageFromUrl(recipe.getImage());
                if (imageIcon != null) {
                    JLabel imageLabel = new JLabel(imageIcon);
                    recipePanel.add(imageLabel, BorderLayout.CENTER);
                }
            }

            panel.add(recipePanel);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        styleScrollPane(scrollPane, backgroundColor);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(frame, scrollPane, "Saved Recipes", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI window = new GUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
