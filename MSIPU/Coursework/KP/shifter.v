module shifter(in,k,shift,out);
input [7:0] in;
input [3:0] k;
input [2:0] shift;
output [7:0] out;
assign out = (k==4'hc) ? in << shift : 
			 (k==4'hd) ? in >> shift : 
			 in;
endmodule