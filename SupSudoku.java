import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SupSudoku extends JFrame implements ActionListener 
{
    JButton[][] myButtons;
    public static int[][][] tab = new int[4][9][9];
    public static int[][] tab_joueur = new int[9][9];
    public static int nombres_placés = 0;

    public static void main(String[] args) 
    {
        SupSudoku mywin = new SupSudoku();
    }

    public int[][][] GenerateTable(JComboBox combo,JPanel panel,int[][] grille,JButton gen_button,JButton[][] game_buttons, JComboBox level,int taille_niveau)
    {
        ///Nombre random
        int[][] grille2;
        int[][][] grilles;
        grille2 = new int[9][9];
        grilles = new int[2][9][9];
        NbrEstValide(grille,0);
        NbrEstValide(grille,0);
        Random test = new Random();
        int[] nbrRand = new int[4];
        int testn = test.nextInt(8)+1;
        nbrRand[0] = testn;
        int test2n = NbrRandomNonPris(nbrRand,test.nextInt(8)+1, test,1);
        nbrRand[1] = test2n;
        int test3n = NbrRandomNonPris(nbrRand,test.nextInt(8)+1, test,2);
        nbrRand[2] = test3n;
        int test4n = NbrRandomNonPris(nbrRand,test.nextInt(8)+1, test,3);
        nbrRand[3] = test4n;

        for (int a = 0; a < 9; a++)
        {
            for (int b = 0; b < 9; b++)
            {
                if (grille[a][b]== nbrRand[0] &&  nbrRand[0] != nbrRand[1])
                {
                    grille[a][b]=00;
                }
            }
        }

        for (int a = 0; a < 9; a++)
        {
            for (int b = 0; b < 9; b++)
            {
                if (grille[a][b]== nbrRand[1] &&  nbrRand[0] != nbrRand[1])
                {
                    grille[a][b]=11;
                }
            }
        }

        for (int a = 0; a < 9; a++)
        {
            for (int b = 0; b < 9; b++)
            {
                if (grille[a][b]== nbrRand[2] &&  nbrRand[2] != nbrRand[3])
                {
                    grille[a][b]=22;
                }
            }
        }

        for (int a = 0; a < 9; a++)
        {
            for (int b = 0; b < 9; b++)
            {
                if (grille[a][b]== nbrRand[3] &&  nbrRand[2] != nbrRand[3])
                {
                    grille[a][b]=33;
                }
            }
        }

        for (int a = 0; a < 9; a++)
        {
            for (int b = 0; b < 9; b++)
            {
                if (grille[a][b]== 00)
                {
                    grille[a][b]=test2n;
                }

                if (grille[a][b]== 11)
                {
                    grille[a][b]=testn;
                }

                if (grille[a][b]== 22)
                {
                    grille[a][b]=test4n;
                }

                if (grille[a][b]== 33)
                {
                    grille[a][b]=test3n;
                }
            }
        }

        gen_button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                tab = GenerateTable(combo,panel,grille,gen_button,game_buttons,level,taille_niveau);
                int niveau = 0;

                for (int b = 0; b < 9; b++)
                {
                    for (int k=0;k<9;k++)
                    {
                        game_buttons[b][k].setText("?");
                        game_buttons[b][k].setForeground(java.awt.Color.black);
                    }
                }
                if (level.getSelectedItem() == "Easy")
                {
                    niveau = 65;
                }
                if (level.getSelectedItem() == "Medium")
                {
                    niveau = 27;
                }
                if (level.getSelectedItem() == "Hard")
                {
                    niveau = 25;
                }

                ///Génération de toute la ligne
                String currentChooseNumberS = "";
                Random setOrNot = new Random();
                int counter = 0;
                int parcoursLigne = 0;
                int parcoursColonne = 0;

                int ligned=0;
                for (int b=0; b<9; b++)
                {
                    if (ligned > 0)
                    {
                        parcoursColonne += 3;
                    }
                    if (parcoursColonne == 9 && parcoursLigne < 6)
                    {
                        parcoursLigne += 3;
                    }
                    if (parcoursColonne == 9)
                    {
                        parcoursColonne = 0;
                    }
                    int nombre_de_cases_avec_nombre = 0;
                    for (int k=0+parcoursLigne;k<3+parcoursLigne;k++) 
                    {
                        for (int g=0+parcoursColonne; g<3+parcoursColonne; g++)
                        {
                            if (counter < niveau)
                            {
                                if (nombre_de_cases_avec_nombre < 9) 
                                {
                                    int currentSetOrNot = setOrNot.nextInt(2);
                                    if(currentSetOrNot == 1)
                                    {
                                        currentChooseNumberS = Integer.toString(grille[k][g]);
                                        game_buttons[k][g].setText(currentChooseNumberS);
                                        game_buttons[k][g].setForeground(java.awt.Color.black);
                                        tab_joueur[k][g] = grille[k][g];
                                        counter++;
                                        nombre_de_cases_avec_nombre++;
                                    }
                                    else
                                    {
                                        tab_joueur[k][g] = 0;
                                    }
                                }
                            }
                            if (game_buttons[k][g].getText() == "?")
                            {
                                String[] choices = { "1","2","3","4","5","6","7","8","9"};
                                ShowChoiceMenu(panel,tab_joueur, myButtons,k,g,choices,combo);
                            }
                        }
                        
                    }
                    ligned++;
                }
            }
        });
        for ( int parc=0; parc<9 ; parc++ )
        {
            for ( int parc2=0; parc2<9; parc2++ )
            {
                grilles[0][parc][parc2] = grille[parc][parc2];
                grilles[1][parc][parc2] = tab_joueur[parc][parc2];
                System.out.print(grille[parc][parc2]+",");

                if (parc2%3==0 && parc2!=0  && parc2!=3 && parc2!=6) System.out.print("\t");

                if (parc2==2 || parc2==5) System.out.print("\t");
                
            }
            System.out.println();
                            
            if (parc%3==0  && parc!=0  && parc!=3 && parc!=6) System.out.println();
    
            if (parc==2 || parc==5) System.out.println();
        }
        System.out.println();
        return grilles;
    }

    public static int NbrRandomNonPris(int nbrRand[],int nombre_courrant, Random generator,int id)
    {
        for (int i=0;i<4 ;i++)
        {
            if (nombre_courrant == nbrRand[i]) 
            {
                nombre_courrant = generator.nextInt(8)+1;
                return NbrRandomNonPris(nbrRand,nombre_courrant,generator,id);
            }
        }
        return nombre_courrant;
    }

    public static boolean NbrAbsentSurLigne (int nombre_courrant, int grille[][], int ligne)
    {
        for (int j=0; j < 9; j++)
            if (grille[ligne][j] == nombre_courrant)
                return false;
        return true;
    }

    public static boolean NbrAbsentSurColonne (int nombre_courrant, int grille[][], int colonne)
    {
        for (int i=0; i < 9; i++)
            if (grille[i][colonne] == nombre_courrant)
                return false;
        return true;
    }

    public static boolean NbrAbsentSurBloc (int nombre_courrant, int grille[][], int ligne, int colonne)
    {
        int ligne_depart = 3*(ligne/3), colonne_depart = 3*(colonne/3);
        for (ligne=ligne_depart; ligne < ligne_depart+3; ligne++)
        {
            for (colonne=colonne_depart; colonne < colonne_depart+3; colonne++)
            {
                if (grille[ligne][colonne] == nombre_courrant) return false;
            }
        }
        return true;
    }

    public static boolean NbrEstValide (int grille[][], int position)
    {
        if (position == 9*9) return true;

        int i = position/9, j = position%9;

        if (grille[i][j] != 0)
            return NbrEstValide(grille, position+1);

        for (int k=1; k <= 9; k++)
        {
            if (NbrAbsentSurLigne(k,grille,i) && NbrAbsentSurColonne(k,grille,j) && NbrAbsentSurBloc(k,grille,i,j))
            {
                grille[i][j] = k;

                if ( NbrEstValide (grille, position+1) ) return true;
            }
        }
        grille[i][j] = 0;

        return false;
    }

    public void ShowChoiceMenu(JPanel mypanel,int[][] grille_joueur,JButton[][] game_buttons,int pos_tab_1,int pos_tab_2,String[] val_affichage,JComboBox level)
    {
        game_buttons[pos_tab_1][pos_tab_2].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JFrame fenetre2 = new JFrame();
                fenetre2.setTitle("NumberChoice");
                fenetre2.setSize(320, 320);
                fenetre2.setLocationRelativeTo(null);
                fenetre2.setVisible(true);
                JPanel panel2 = new JPanel(new GridLayout(2, 5));
                panel2.setLayout(null);
                fenetre2.add(panel2);

                JButton choose = new JButton("Choose");
                choose.setFont(new Font("Ubuntu", Font.BOLD, 30));
                choose.setBounds(60,120,200,40);

                JComboBox combo2 = new JComboBox(val_affichage);
                combo2.setBounds(110,50, 100,40);
                combo2.setFont(new Font("Ubuntu", Font.BOLD, 50));
                panel2.add(combo2);
                panel2.add(choose);

                //Second Button Action Start
                choose.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    Object contents2 = combo2.getSelectedItem();
                    String contents_out = contents2.toString();
                    // System.out.println(contents_out);
                    game_buttons[pos_tab_1][pos_tab_2].setText(contents_out);
                    game_buttons[pos_tab_1][pos_tab_2].setForeground(java.awt.Color.blue);
                    grille_joueur[pos_tab_1][pos_tab_2] = Integer.parseInt(contents_out);
                    mypanel.remove(combo2);
                    fenetre2.setVisible(false);
                    nombres_placés++;
                    System.out.println(nombres_placés);
                    CheckWinnerOrLooser(tab,tab_joueur,nombres_placés,level,game_buttons);
                    }
                });
                //Second Button Action End
            }
        });
    }

    public void ShowSudokuSolution(int[][][] tab_complet,int[][] tabs_joueur,int[][] grille,JButton[][] game_buttons)
    {
        for (int parcoursLigne=0;parcoursLigne<9 ;parcoursLigne++ )
        {
            for (int parcoursColonne=0;parcoursColonne<9 ;parcoursColonne++ )
            {
                if( tab_complet[ 0 ][ parcoursLigne ][ parcoursColonne ] != tabs_joueur[ parcoursLigne ][ parcoursColonne ])
                {
                    String currentChooseNumberS = "";
                    currentChooseNumberS = Integer.toString(grille[parcoursLigne][parcoursColonne]);
                    game_buttons[parcoursLigne][parcoursColonne].setText(currentChooseNumberS);
                    game_buttons[parcoursLigne][parcoursColonne].setForeground(java.awt.Color.red);
                }
            }
        }
    }

    public void CheckWinnerOrLooser(int[][][] tab_complet,int[][] tabs_joueur,int nombres_placés,JComboBox level,JButton[][] game_buttons)
    {
        int bien_placé = 0;
        int niveau = 0;
        if (level.getSelectedItem() == "Easy")
        {
            niveau = 65;
        }
        if (level.getSelectedItem() == "Medium")
        {
            niveau = 27;
        }
        if (level.getSelectedItem() == "Hard")
        {
            niveau = 21;
        }

        for ( int parcoursLigne=0; parcoursLigne<9; parcoursLigne++ )
        {
            for ( int parcoursColonne=0; parcoursColonne<9; parcoursColonne++ )
            {
                if( tab_complet[ 0 ][ parcoursLigne ][ parcoursColonne ] == tabs_joueur[ parcoursLigne ][ parcoursColonne ])
                {
                    bien_placé++;
                }

            }
        }
        if( bien_placé == 81)
        {
            JFrame fenetrewin = new JFrame();
            fenetrewin.setTitle("Félicitations");
            fenetrewin.setSize(400, 200);
            fenetrewin.setLocationRelativeTo(null);
            JPanel panelwin = new JPanel(new GridLayout(2, 5));
            panelwin.setLayout(null);
            fenetrewin.add(panelwin);
            JLabel labelwin = new JLabel("Tu as gagné ;)");
            labelwin.setFont(new Font("Arial", Font.ITALIC, 25));
            panelwin.add(labelwin);
            fenetrewin.add(labelwin);
            fenetrewin.setVisible(true);
        }
        else if( nombres_placés == (81 - niveau) && bien_placé != 81 )
        {
            JFrame fenetreloose = new JFrame();
            fenetreloose.setTitle("Essaie encore");
            fenetreloose.setSize(400, 200);
            fenetreloose.setLocationRelativeTo(null);
            JPanel panelloose = new JPanel(new GridLayout(2, 5));
            panelloose.setLayout(null);
            fenetreloose.add(panelloose);
            JLabel labelloose = new JLabel("Tu as perdu );");
            labelloose.setFont(new Font("Arial", Font.ITALIC, 25));
            panelloose.add(labelloose);
            fenetreloose.add(labelloose);
            fenetreloose.setVisible(true);
            ShowSudokuSolution(tab_complet,tabs_joueur,tab_complet[ 0 ],game_buttons);
        }
    }

    public SupSudoku()
    {
        JFrame fenetre = new JFrame();
        int[][] grille = new int[9][9];
        //Définit un titre pour notre fenêtre
        fenetre.setTitle("SupSudoku");
        //Définit sa taille : 400 pixels de large et 100 pixels de haut
        fenetre.setSize(760, 950);
        //Nous demandons maintenant à notre objet de se positionner au centre
        fenetre.setLocationRelativeTo(null);
        //Termine le processus lorsqu'on clique sur la croix rouge
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 5));
        panel.setLayout(null);
        fenetre.add(panel);

        String[] levels = { "Easy","Medium","Hard"};
        JComboBox combo = new JComboBox(levels);
        combo.setBounds(50,25, 120,40);
        combo.setFont(new Font("Arial", Font.ITALIC, 25));
        panel.add(combo);

        JButton generate = new JButton("Démarrer");
        generate.setFont(new Font("Arial", Font.ITALIC, 25));
        generate.setBounds(300,25, 180,40);
        panel.add(generate);

        myButtons = new JButton[9][9];

        int[][][] recup_grilles = GenerateTable(combo,panel,grille,generate,myButtons,combo,0);

        int x = 20; int y = 90; int compteur=0;
        String[] choices = { "1","2","3","4","5","6","7","8","9"};
        for (int i = 0; i < 9; i ++)
        {
            for (int j = 0; j < 9; j ++)
            {
                myButtons[i][j] = new JButton();
                myButtons[i][j].setFont(new Font("Arial", Font.ITALIC, 25));
                myButtons[i][j].setBounds(x,y,50,50);
                myButtons[i][j].setText("?");
                panel.add(myButtons[i][j]);
                
                x += 60;
                compteur +=1;
                if (compteur % 3 == 0)
                {
                    x+=80;
                }
                if(compteur % 9 == 0)
                {
                    y+=60;
                    x=20;
                }
                if(compteur == 27 || compteur == 54 || compteur == 81 )
                {
                    y+=80;
                }
            }
        }
        fenetre.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) 
    {}
}
