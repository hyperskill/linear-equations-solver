package solver;

public class iRow extends Row {
    private ComplexNumber[] iData;
    private iMatrix owner;

    public iRow(int rowNum, int colCnt, iMatrix owner) {
        super(rowNum, colCnt, owner);
        this.isComplex=true;
        this.iData = new ComplexNumber[colCnt];
    }

    public void mult(ComplexNumber koef){
        for (int i = 0; i < iData.length; i++) {
            iData[i].mult(koef);
        }
    }

    public void add(iRow r, ComplexNumber koef){
        for (int i = 0; i < iData.length; i++) {
            iData[i].add(ComplexNumber.mult(r.iData[i],koef));
            //data[i]+=koef*r.data[i];
        }
    }

    public void fillRow(String s) throws ParsingArrayException {
        String[] str = s.split("\\s+");
        try {
            if (str.length!=this.colCnt)
                throw new ParsingArrayException(
                        String.format("The string '%s' cannot be parsed correctly. It has to contain %d numbers", s, this.colCnt+1),
                        null);
            for (int i = 0; i < str.length; i++) {
                /*if (!isComplex)
                    this.data[i] = Double.parseDouble(str[i]);
                else*/
                    this.iData[i] = new ComplexNumber(str[i]);
            }
        } catch(NumberFormatException e){
            throw new ParsingArrayException(
                    String.format("The string '%s' cannot be parsed as an array of numbers", s),
                    e);
        }
        catch (ArrayIndexOutOfBoundsException e){
            throw new ParsingArrayException(
                    String.format("The string '%s' cannot be parsed correctly, count of numbers is incorrect", s),
                    e);
        }
    }

    public String transform1(int index){
        if (ComplexNumber.compareComplexToDouble(this.iData[index],1)) return "";//"already transformed1";
        if (ComplexNumber.compareComplexToDouble(this.iData[index],0)){
            int nonZeroInd = this.owner.findNonZeroBelow(index,this);
            if (nonZeroInd > -1)
                return this.owner.swapRows(this.rowNum-1,nonZeroInd);
            nonZeroInd = this.owner.findNonZeroRighter(index,this);
            if (nonZeroInd > -1)
                return this.owner.swapColumns(index,nonZeroInd);
            //поиск по всей матрице
            Pos pos = this.owner.findNonZeroEverywhere(index,this);
            if (pos == null) return "";
            return this.owner.swapColumns(index,pos.y)+";"+
                    this.owner.swapRows(this.rowNum-1, pos.x);
        }
        //ComplexNumber koef = 1/this.iData[index];
        ComplexNumber koef = ComplexNumber.div(new ComplexNumber(1,0),this.iData[index]);
        this.mult(koef);
        this.iData[index].set(1,0);
//        return String.format("%-8.2f %n",koef)  +" * "+this.getName()+" -> "+this.getName();
        return String.format("%d: %.2f * %s -> %s",++this.owner.iteration, koef,this.getName(),this.getName());
    }

    public String transform0(int index, iRow r){
        if (ComplexNumber.compareComplexToDouble(this.iData[index],0)) return "";//"already transformed0";
        //double koef = -1 * this.iData[index];
        ComplexNumber koef = ComplexNumber.div(new ComplexNumber(-1,0),this.iData[index]);
        this.add(r,koef);
        this.iData[index].set(0,0);
        this.owner.iteration++;
//        return koef + " * "+ r.getName() + " + "+ this.getName()+" -> "+this.getName();
        return String.format("%d: %.3f * %s -> %s",this.owner.iteration, koef,r.getName(),this.getName());
    }

    public String toString() {
        String s="";
        /*if (!isComplex)
            for (double d:this.data) {
                s +=String.format("%-10.3f ",d);
                //s +=String.format("%.3f ",d);
            };
        else {*/
            for (ComplexNumber iDat : iData) {
                s +=iDat.toString();
            }
        return this.getName()+((this.getOldRowNum()!=this.getRowNum())?String.format("(%d)",this.getOldRowNum()):"")+": "+ s.trim();
    }

    public String getColStr(int index){
        return this.iData[index].toString();
    }

    public ComplexNumber getColComplex(int index){
        return this.iData[index];
    }
}
