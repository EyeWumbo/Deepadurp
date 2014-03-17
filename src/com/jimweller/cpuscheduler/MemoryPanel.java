package com.jimweller.cpuscheduler;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class MemoryPanel extends JPanel{

	final static int width=170,height=80;


    JLabel memLabel,cpuTotalLabel,cpuUsedLabel,cpuCurrent, cpuCurrentMax;

    public void setMem(int m)
    {
    		cpuCurrentMax.setText(""+m);
    }
    
    MemoryPanel(String title){
        TitledBorder tBorder =  BorderFactory.createTitledBorder(title);
        setBorder( tBorder);
        setLayout(new GridLayout(0,2));

        cpuUsedLabel  = new JLabel("Usage");
        cpuCurrent =  new JLabel(""+0);
        cpuTotalLabel = new JLabel("Max");
        cpuCurrentMax = new JLabel("" + 0);

        add(cpuUsedLabel);
        add(cpuCurrent);
        add(cpuTotalLabel);
        add(cpuCurrentMax);

        setSize(width,height);
        setMinimumSize(new Dimension(width,height));
    }
    
    public void setStats(int currentUsage, int currentMax){
        cpuCurrent.setText(Integer.toString(currentUsage));
        cpuCurrentMax.setText(Integer.toString(currentMax));
    }
    
    public Dimension getMinimumSize(){
        return new Dimension(width,height);
    }

    public Dimension getPreferredSize(){
        return new Dimension(width,height);
    }
	
}
