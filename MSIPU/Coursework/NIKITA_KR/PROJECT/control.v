module control(clk, k, c, z, n, branch, q_pc, int_save, int_load, int_v);
input clk, c, z, n, int_save, int_load;
input [15:0] k;
input [7:0] int_v;
output [7:0] q_pc; reg [7:0] q_pc;
reg [7:0] prev_q_pc;

output branch; 
assign branch =
	(k[15:12]==4'hf)&((k[11:8]==4'h0)|
	(k[11:8]==1)& z| (k[11:8]==2)& ~z|
	(k[11:8]==3)& c| (k[11:8]==4)&~c|
	(k[11:8]==5)& n|(k[11:8]==6)& ~n);
always @ (posedge clk) 
begin
	if (branch) q_pc = k;
		else q_pc = q_pc+1; 
		
	if(int_save)
	begin
		prev_q_pc = q_pc;
		q_pc = int_v;
	end
	else if (int_load)
	begin
		q_pc = prev_q_pc;
	end

end

endmodule