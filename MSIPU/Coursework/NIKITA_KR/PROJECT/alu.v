module alu (k,x,y,dm, d_bus, cl,zl,nl);
input [15:0] k;
input [7:0] x, y, dm;
output [7:0] d_bus; reg [7:0] d_bus;
output cl; reg cl; output zl, nl;
always if (k [15]==0) case(k [14:12])
	0: {cl,d_bus} = {1'b0,k [7:0]};
	1: {cl,d_bus} = x + k [7:0];
	2: {cl,d_bus} = x - k [7:0] ;
	3: {cl,d_bus} = {1'b0,( x & k [7:0])};
	4: {cl,d_bus} = {1'b0,( x | k [7:0])};
	5: {cl,d_bus} = {1'b0,( x ^ k [7:0])};
endcase else
if (k[15:12] == 8) case (k [2:0])
	0: d_bus=y;
	1: {cl,d_bus} = x + y;
	2: {cl,d_bus} = x - y;
	3: {cl,d_bus} = {1'b0,( x & y)};
	4: {cl,d_bus} = {1'b0,( x | y)};
	5: {cl,d_bus} = {1'b0,( x ^ y)};
endcase else
if (k[15:12] == 9) case (k [2:0])
	0: {cl,d_bus,}={1'b0,( x+1)};
	1: {cl,d_bus}= {1'b0,( x-1)};
	2: {cl,d_bus}= {1'b0,( ~x)};
	3: {d_bus,cl}= {x[7], x[7:0]};
	4: {cl,d_bus}= {x,1'b0};
	5: {d_bus,cl} = {x};
endcase else
if (k[15:12]==4'b1010) case (k [0])
	0: d_bus=dm; 
	1: d_bus=x;
endcase
else d_bus=0;
assign zl=(d_bus==0);
assign nl = d_bus[7];
endmodule
