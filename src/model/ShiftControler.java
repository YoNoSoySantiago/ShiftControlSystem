package model;
import exceptions.*;

import java.util.ArrayList;

public class ShiftControler {
	private ArrayList<User> users;
	private ArrayList<User> userShift;
	private Shift shift;
	
	public ShiftControler() {
		users = new ArrayList<User>();
		userShift = new ArrayList<User>();
		shift = new Shift('A',0,"A00",false);
	}
	public void assignShift(String documentNumber,String documentType) throws UserAlreadyHasShiftException {
		for(int j =0;j<userShift.size();j++) {
			int letterUser = (int)userShift.get(j).getShift().getLetter();
			int letterShift = (int)shift.getLetter();
			if(userShift.get(j).getDocumentNumber()==(documentNumber) && letterShift>= letterUser && userShift.get(j).getDocumentType()==documentType) {
				if(letterShift== letterUser) {
					if(shift.getNumber()>userShift.get(j).getShift().getNumber()) {
						throw new UserAlreadyHasShiftException();
					}
				}else{
					throw new UserAlreadyHasShiftException();
				}
						
			}
		}
		for(int i = 0;i<users.size();i++) {
			if(users.get(i).getDocumentNumber()==(documentNumber)&&users.get(i).getDocumentType()==documentType) {
				User user = users.get(i);
				user.setShift(generateNextShift());
				userShift.add(user);
			}
		}
	}
	//This method is to generate the next Shift in the list 
	public Shift generateNextShift() {
		if(userShift.size()>0) {
			Shift shift = userShift.get(userShift.size()-1).getShift();
			shift.setNumber(shift.getNumber()+1);
			if(shift.getNumber()>99) {
				shift.setNumber(0);
				shift.setLetter((char) (shift.getLetter()+1));
			}
			if(shift.getNumber()<10) {
				shift.setShift((char)(shift.getLetter())+ "0"+Integer.toString(shift.getNumber()).toUpperCase()); 
			}
			
			return shift;
		}else {
			return shift;
		}
		
	}
	
	public void registerUser(String name,String lastName,String documentType,String documentNumber,String locate,String numberPhone) throws IdUserExistException,ValueIsEmptyException {
		for(int i =0;i<users.size();i++) {
			if(users.get(i).getDocumentNumber() == documentNumber && (users.get(i).getDocumentType() == documentType)){
				throw new IdUserExistException();
			}
		}
		if(name.isEmpty()||lastName.isEmpty()||documentType.isEmpty()||documentNumber.isEmpty()) {
			throw new ValueIsEmptyException();
		}
		if(locate.isEmpty()) {
			locate = User.UNKNOWN;
		}
		if(numberPhone.isEmpty()) {
			numberPhone = User.UNKNOWN;
		}
		users.add(new User(name,lastName,documentType,documentNumber,locate,numberPhone,null));
	}
	public void advanceShift() throws NoMoreShiftException {
		if(userShift.size()>0) {
			int letterUser = (int)userShift.get(userShift.size()-1).getShift().getLetter();
			int letterShift = (int)shift.getLetter();
			if(letterShift>= letterUser) {
				if(letterShift== letterUser) {
					if(shift.getNumber()==userShift.get(userShift.size()-1).getShift().getNumber()) {
						throw new NoMoreShiftException();
					}
				}
				
			}
		}else {
			throw new NoMoreShiftException();
		}
		
		shift.setNumber(shift.getNumber()+1);
		if(shift.getNumber()>99) {
			shift.setNumber(0);
			shift.setLetter((char) (shift.getLetter()+1));
		}
		if(shift.getNumber()<10) {
			shift.setShift((char)(shift.getLetter())+ "0"+Integer.toString(shift.getNumber()).toUpperCase()); 
		}
	}
	
	
	
}

