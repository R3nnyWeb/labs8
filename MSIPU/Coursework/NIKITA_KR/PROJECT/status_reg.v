module status_reg(clk, wreg,zl,cl,nl,z,c,n, int_save, int_load);
input clk, wreg, nl, zl, cl, int_load, int_save;
output n; reg n;
output z; reg z;
output c; reg c;
reg prev_n;
reg prev_z;
reg prev_c;
always @ (posedge clk)
begin
	if (wreg==1) begin
		 n=nl; z=zl; c=cl;
		if(int_save)
		begin
			prev_n = n;
			prev_c = c;
			prev_z = z;
			n=0; z=0; c=0;
		end
		else if (int_load)
		begin
			n = prev_n;
			c = prev_c;
			z = prev_z;
		end 
			
	end
	
end

endmodule
