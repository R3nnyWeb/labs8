module control(clk, k, c, z, n, branch, q_pc);
input clk, c, z, n;
input [15:0] k;
output [7:0] q_pc; reg [7:0] q_pc;
output branch; 
assign branch =
	(k[15:12]==1'hb)&((k[11:8]==4'h0)|
	(k[11:8]==1)& z| (k[11:8]==2)& ~z|
	(k[11:8]==3)& c| (k[11:8]==4)&~c|
	(k[11:8]==5)& n|(k[11:8]==6)& ~n);
always @ (posedge clk)
if (branch) q_pc = k;
else q_pc = q_pc+1; 
endmodule